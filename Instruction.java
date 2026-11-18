public class Instruction {

    String opcode;
    String op1;
    String op2;

    public Instruction(String opcode, String op1, String op2) {
        this.opcode = opcode.toUpperCase();
        this.op1 = op1;
        this.op2 = op2;
    }
}