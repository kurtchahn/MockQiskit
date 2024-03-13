package QuantumComputerSimulator;

import java.text.DecimalFormat;

public class ComplexNumber {

    private double real;
    private double imaginary;

    public ComplexNumber(double real, double imaginary){
        this.real = real;
        this.imaginary = imaginary;
    }

    public double getReal() {
        return this.real;
    }

    public double getImaginary() {
        return this.imaginary;
    }

    public void setReal(double newReal) {
        this.real = newReal;
    }

    public void setImaginary(double newImaginary) {
        this.imaginary = newImaginary;
    }

    public void print() {
        DecimalFormat dfReal = new DecimalFormat("0.00");
        DecimalFormat dfImaginary = new DecimalFormat("0.00");

        if (this.real >= 0) System.out.print("+");
        else System.out.print("-");
        System.out.print(dfReal.format(Math.abs(this.real)));
        if (this.imaginary >= 0) System.out.print("+");
        else System.out.print("-");
        System.out.print(dfImaginary.format(Math.abs(this.imaginary))+"i, ");
    }

    public static void printArray(ComplexNumber[] array) {
        for (ComplexNumber i : array) i.print();
    }

    public ComplexNumber addBy(ComplexNumber other) {
        double newReal = this.real + other.real;
        double newImaginary = this.imaginary + other.imaginary;

        return new ComplexNumber(newReal, newImaginary);
    }

    public ComplexNumber multiplyBy(ComplexNumber other) {
        double newReal = this.real * other.real - this.imaginary * other.imaginary;
        double newImaginary = this.imaginary * other.real + this.real * other.imaginary;

        return new ComplexNumber(newReal, newImaginary);
    }

    public static ComplexNumber[] gateOperation(ComplexNumber[][] operation, ComplexNumber[] state) {

        ComplexNumber[] newState = new ComplexNumber[state.length];
        
        for (int i=0; i<state.length; i++) {
            newState[i] = new ComplexNumber(0, 0);
            for (int j=0; j<state.length; j++)
                newState[i] = newState[i].addBy(state[j].multiplyBy(operation[i][j]));
        }
        return newState;
}

    public static ComplexNumber[][] tensorOver(ComplexNumber[][] matrix1, ComplexNumber[][] matrix2) {

        int newDimension = matrix1.length * matrix2.length;

        ComplexNumber[][] newMatrix = new ComplexNumber[newDimension][newDimension];

        for (int x=0; x<matrix1.length; x++)
            for (int y=0; y<matrix1.length; y++)
                for (int a=0; a<matrix2.length; a++)
                    for (int b=0; b<matrix2.length; b++)
                        newMatrix[matrix2.length*x+a][matrix2.length*y+b] = matrix1[x][y].multiplyBy(matrix2[a][b]);

        return newMatrix;
    }
}
