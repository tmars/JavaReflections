package com.talipov;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Марсель on 10.02.2017.
 */
public class People {
    private String name;
    private Integer age;
    private double salary;

    public People(String name, Integer age, double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public People() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    protected void paySalary() {
        System.out.println("salary "+salary);
    }

    public void serialize(String filename) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        DOMImplementation impl = builder.getDOMImplementation(); // более сложный, но и более гибкий способ создания документов
        Document doc = impl.createDocument(null, // namespaceURI
                null, // qualifiedName
                null); // doctype
        Element e1 = doc.createElement("object");
        e1.setAttribute("type", "People");
        doc.appendChild(e1);

        Element e2 = doc.createElement("field");
        e2.setAttribute("type", "String");
        e2.setAttribute("id", "name");
        e2.setAttribute("value", name);
        e1.appendChild(e2);

        Element e3 = doc.createElement("field");
        e3.setAttribute("type", "Integer");
        e3.setAttribute("id", "age");
        e3.setAttribute("value", Integer.toString(age));
        e1.appendChild(e3);

        Element e4 = doc.createElement("field");
        e4.setAttribute("type", "Double");
        e4.setAttribute("id", "salary");
        e4.setAttribute("value", Double.toString(salary));
        e1.appendChild(e4);

        try {
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            tr.setOutputProperty(OutputKeys.METHOD, "xml");
            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            // send DOM to file
            tr.transform(new DOMSource(doc),
                    new StreamResult(new FileOutputStream(filename)));

        } catch (TransformerException te) {
            System.out.println(te.getMessage());
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    };

    public void deserialize(String filename) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); //создали фабрику строителей, сложный и грамосткий процесс (по реже выполняйте это действие)
        // f.setValidating(false); // не делать проверку валидации
        DocumentBuilder db = null; // создали конкретного строителя документа
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = null; // стооитель построил документ
        try {
            doc = db.parse(new File(filename));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Document - тоже является нодом, и импленментирует методы
        visit(doc, 0);
    }

    public void visit(Node node, int level) {
        NodeList list = node.getChildNodes();

        Node childNode = list.item(0); // объект
        NodeList fields = childNode.getChildNodes();

        name = fields.item(1).getAttributes().getNamedItem("value").getNodeValue();
        age = Integer.parseInt(
                fields.item(3).getAttributes().getNamedItem("value").getNodeValue()
        );
        salary = Double.parseDouble(
                fields.item(5).getAttributes().getNamedItem("value").getNodeValue()
        );
    }

    @Override
    public String toString() {
        return "Name: " + name + " Age: " + age + " Salaray: " + salary;
    }
}
