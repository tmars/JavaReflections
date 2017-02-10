package com.talipov;

public class Main {

    public static void main(String[] args) {
	    People people = new People("Artur", 30, 100000.00);
        try {
            people.getClass().getConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        people.serialize("test.dat");
    }
}
