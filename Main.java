package QuantumComputerSimulator;

public class Main {

    public static void main(String[] args) {
        
        QuantumCircuit qc = new QuantumCircuit(3);

        qc.hAll();
        qc.ccx(0,1,2);
        qc.z(0);
        qc.p(1,90);

        qc.run();
    }
}
