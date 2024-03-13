package com.kurt.main;

public class QuantumCircuit {
    
    private int numQubits;
    private int stateDimension;
    private double[][] state;

    // Initialization Statement
    public QuantumCircuit(int numQubits)
    {
        this.numQubits = numQubits;
        this.stateDimension = (int) Math.pow(2, numQubits);
        this.state = new double[stateDimension][2];

        for (double[] entry : state)
        {
            entry[0] = 0; entry[1] = 0;
        }
        state[0][0] = 1;
    }

    public void printState()
    {
        for (double[] entry : state)
        {
            for (double component : entry)
            {
                System.out.print(component + " | ");
            }
            System.out.println("");
        }
    }

    
    public void x(int target)
    {
        int blockLength = (int) Math.pow(2, target);
        int numBlocks = (int) stateDimension/blockLength;
        int numPairs = (int) numBlocks/2;

        double[][] store = new double[blockLength][2];

        for (int pair=0; pair < numPairs; pair++)
        {
            // Store contents of first block into store block
            for (int brick=0; brick < blockLength; brick++)
            {
                int index = (pair * blockLength) + brick;
                
                store[brick] = state[index];
            }

            // Copy contents of second block into first block
            for (int brick=0; brick < blockLength; brick++)
            {
                int firstIndex = (pair * blockLength) + brick;
                int secondIndex = (pair * blockLength) + blockLength + brick;

                state[firstIndex] = state[secondIndex];
            }
            
            // Copy contents of store brick into second block
            for (int brick=0; brick < blockLength; brick++)
            {
                int index = (pair * blockLength) + blockLength + brick;

                state[index] = store[brick];
            }
        }
    }

}
