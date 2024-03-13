package com.kurt.main;

public class Main {

    public static void main(String[] args)
    {

        QuantumCircuit qc = new QuantumCircuit(2);

        qc.x(0);
        qc.printState();
    }
}
