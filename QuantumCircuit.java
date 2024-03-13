package QuantumComputerSimulator;

public class QuantumCircuit {

    private int numQubits;
    private int stateDimension;
    private ComplexNumber[] state;

    // Initialization Statement
    public QuantumCircuit(int numQubits) {
        this.numQubits = numQubits;
        this.stateDimension = (int) (Math.pow(2, numQubits));
        this.state = new ComplexNumber[stateDimension];

        for (int i=0; i<stateDimension; i++) 
            state[i] = new ComplexNumber(0,0);
        state[0].setReal(1);
    }

    // Create I Gate
    public ComplexNumber[][] createI(int qubits) {
        int dimension = (int) (Math.pow(2, qubits));

        ComplexNumber[][] matrixI = new ComplexNumber[dimension][dimension];

        for (int i=0; i<dimension; i++)
            for (int j=0; j<dimension; j++)
                if (i==j) matrixI[i][j] = new ComplexNumber(1, 0);
                else matrixI[i][j] = new ComplexNumber(0, 0);

        return matrixI;
    }

    // Apply X Gate
    public void x(int target) {
        ComplexNumber[][] operator = new ComplexNumber[2][2];
        for (int i=0; i<2; i++) {
            for (int j=0; j<2; j++) {
                if ((i+j) % 2 == 1) operator[i][j] = new ComplexNumber(1, 0);
                else operator[i][j] = new ComplexNumber(0, 0);
            }
        }
        operator = ComplexNumber.tensorOver(createI(numQubits-1-target), operator);
        operator = ComplexNumber.tensorOver(operator, createI(target));
        state = ComplexNumber.gateOperation(operator, state);
    }
    
    // Apply Y Gate
    public void y(int target) {
        ComplexNumber[][] operator = new ComplexNumber[2][2];
        for (int i=0; i<2; i++) {
            for (int j=0; j<2; j++) {
                operator[i][j] = new ComplexNumber(0, (double) (i-j));
            }
        }
        operator = ComplexNumber.tensorOver(createI(numQubits-1-target), operator);
        operator = ComplexNumber.tensorOver(operator, createI(target));
        state = ComplexNumber.gateOperation(operator, state);
    }
    
    // Apply Z Gate
    public void z(int target) {
        ComplexNumber phaser = new ComplexNumber(-1, 0);
        for (int i=0; i<stateDimension; i++) {
            int[] indexBinary = BinaryOperations.convertToBinary(i, numQubits);
            if (indexBinary[target] == 1) state[i] = state[i].multiplyBy(phaser);
        }
    }

    // Apply H Gate
    public void h(int target) {
        ComplexNumber[][] operator = new ComplexNumber[2][2];
        for (int i=0; i<2; i++)
            for (int j=0; j<2; j++)
                if (i*j == 1) operator[i][j] = new ComplexNumber(-1/Math.sqrt(2), 0);
                else operator[i][j] = new ComplexNumber(1/Math.sqrt(2), 0);
        operator = ComplexNumber.tensorOver(createI(numQubits-1-target), operator);
        operator = ComplexNumber.tensorOver(operator, createI(target));
        state = ComplexNumber.gateOperation(operator, state);
    }

    // Apply H Gate to all qubits
    public void hAll() {
        for (int qubit=0; qubit<numQubits; qubit++)
            this.h(qubit);
    }

    // Apply P Gate
    public void p(int target, double phaseAngle) {
        phaseAngle /= 180;
        phaseAngle *= Math.PI;
        ComplexNumber phaser = new ComplexNumber(Math.cos(phaseAngle), Math.sin(phaseAngle));

        for (int i=0; i<stateDimension; i++) {
            int[] indexBinary = BinaryOperations.convertToBinary(i, numQubits);
            if (indexBinary[target] == 1) state[i] = state[i].multiplyBy(phaser);
        }

    }

    // Apply CX Gate
    public void cx(int control, int target) {
        ComplexNumber[][] operator = new ComplexNumber[stateDimension][stateDimension];

        for (int i=0; i<stateDimension; i++) {
            int[] controlIndexBinary = BinaryOperations.convertToBinary(i, numQubits);
            if (controlIndexBinary[control] == 1) {
                controlIndexBinary[target] += 1;
                controlIndexBinary[target] %= 2;
            }
            for (int j=0; j<stateDimension; j++) {
                int[] targetIndexBinary = BinaryOperations.convertToBinary(j, numQubits);
                if (BinaryOperations.compare(controlIndexBinary,targetIndexBinary))
                    operator[i][j] = new ComplexNumber(1, 0);
                else
                    operator[i][j] = new ComplexNumber(0, 0);

            }
        }
        state = ComplexNumber.gateOperation(operator, state);
    }

    // Apply CY Gate
    public void cy(int control, int target) {
        ComplexNumber[][] operator = new ComplexNumber[stateDimension][stateDimension];

        for (int i=0; i<stateDimension; i++) {
            int[] controlIndexBinary = BinaryOperations.convertToBinary(i, numQubits);
            if (controlIndexBinary[control] == 1) {
                controlIndexBinary[target] += 1;
                controlIndexBinary[target] %= 2;
            }
            for (int j=0; j<stateDimension; j++) {
                int[] targetIndexBinary = BinaryOperations.convertToBinary(j, numQubits);
                if (BinaryOperations.compare(controlIndexBinary,targetIndexBinary))
                    if (controlIndexBinary[control] == 0)
                        operator[i][j] = new ComplexNumber(1, 0);                    
                    else if (targetIndexBinary[target] == 1)
                        operator[i][j] = new ComplexNumber(0, -1);
                    else
                        operator[i][j] = new ComplexNumber(0, 1);
                else
                    operator[i][j] = new ComplexNumber(0, 0);

            }
        }
        state = ComplexNumber.gateOperation(operator, state);
    }

    // Apply CZ Gate
    public void cz(int control, int target) {
        ComplexNumber[][] operator = new ComplexNumber[stateDimension][stateDimension];

        for (int i=0; i<stateDimension; i++) {
            int[] controlIndexBinary = BinaryOperations.convertToBinary(i, numQubits);
            for (int j=0; j<stateDimension; j++) {
                int[] targetIndexBinary = BinaryOperations.convertToBinary(j, numQubits);

                if (BinaryOperations.compare(controlIndexBinary, targetIndexBinary)) {
                    if (controlIndexBinary[control] == 1 & targetIndexBinary[target] == 1)
                        operator[i][j] = new ComplexNumber(-1, 0);
                    else
                        operator[i][j] = new ComplexNumber(1, 0);
                } 
                else
                    operator[i][j] = new ComplexNumber(0, 0);
            }
        }
        state = ComplexNumber.gateOperation(operator, state);
    }

    // Apply SWAP Gate
    public void swap(int qubit1, int qubit2) {
        ComplexNumber[][] operator = new ComplexNumber[stateDimension][stateDimension];

        for (int i=0; i<stateDimension; i++) {
            int[] qubit1IndexBinary = BinaryOperations.convertToBinary(i,numQubits);
            
            int temp = qubit1IndexBinary[qubit1];
            qubit1IndexBinary[qubit1] = qubit1IndexBinary[qubit2];
            qubit1IndexBinary[qubit2] = temp;

            for (int j=0; j<stateDimension; j++) {
                int[] qubit2IndexBinary = BinaryOperations.convertToBinary(j,numQubits);
                if (BinaryOperations.compare(qubit1IndexBinary, qubit2IndexBinary))
                    operator[i][j] = new ComplexNumber(1, 0);
                else
                    operator[i][j] = new ComplexNumber(0, 0);
            }

        }
        state = ComplexNumber.gateOperation(operator, state);
    }

    // Apply CCX Gate
    public void ccx(int control1, int control2, int target) {
        ComplexNumber[][] operator = new ComplexNumber[stateDimension][stateDimension];

        for (int i=0; i<stateDimension; i++) {
            int[] controlIndexBinary = BinaryOperations.convertToBinary(i, numQubits);
            if (controlIndexBinary[control1] == 1 && controlIndexBinary[control2] == 1) {
                controlIndexBinary[target] += 1;
                controlIndexBinary[target] %= 2;
            }
            for (int j=0; j<stateDimension; j++) {
                int[] targetIndexBinary = BinaryOperations.convertToBinary(j, numQubits);
                if (BinaryOperations.compare(controlIndexBinary,targetIndexBinary))
                    operator[i][j] = new ComplexNumber(1, 0);
                else
                    operator[i][j] = new ComplexNumber(0, 0);

            }
        }
        state = ComplexNumber.gateOperation(operator, state);
    }

    public int getNumQubits() {
        return numQubits;
    }

    public void run() { 
        
        for (int i=0; i<stateDimension; i++) {
            BinaryOperations.print(BinaryOperations.convertToBinary(i, numQubits));
            System.out.print(" | ");
            state[i].print();
            System.out.println("");
        }
    }
}
