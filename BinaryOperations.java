package QuantumComputerSimulator;

public class BinaryOperations {

    // Converts Integer to Binary to the nth least significant digit in a reversed Array
    public static int[] convertToBinary(int integer, int numSigFigs) {

        int[] result = new int[numSigFigs];

        for (int n=0; n<numSigFigs; n++) {
            result[n] = integer % 2;
            integer /= 2;
        }

        return result;
    }

    public static void print(int[] binaryString) {
        for (int i=binaryString.length; i>0; i--) 
            System.out.print(binaryString[i-1]);
    }

    public static boolean compare(int[] array1, int[] array2) {
        boolean equals = true;
        for (int i=0; i<array1.length; i++)
            if (array1[i] != array2[i]) equals = false;
        return equals;
    }

}
