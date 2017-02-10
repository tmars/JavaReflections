package com.talipov;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
            tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "roles.dtd");
            tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            // send DOM to file
            tr.transform(new DOMSource(doc),
                    new StreamResult(new FileOutputStream(filename)));

        } catch (TransformerException te) {
            System.out.println(te.getMessage());
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    };
}
