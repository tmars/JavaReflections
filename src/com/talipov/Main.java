package com.talipov;

public class Main {

    public static void main(String[] args) {
	    People p1 = new People("Artur", 30, 100000.00);
	    p1.serialize("test.dat");
        System.out.println("people 1");
        System.out.println(p1);

        People p2= new People();
        p2.deserialize("test.dat");
        System.out.println("people 2");
        System.out.println(p2);
    }
}
