package com.talipov;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        String filename = "test.dat";
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        People p1 = new People("Artur", 30, 100000.00);
	    XMLSerializer.serialize(outputStream, p1);
        System.out.println("people 1");
        System.out.println(p1);
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        People p2 = (People) XMLSerializer.deserialize(inputStream);
        System.out.println("people 2");
        System.out.println(p2);
    }
}
