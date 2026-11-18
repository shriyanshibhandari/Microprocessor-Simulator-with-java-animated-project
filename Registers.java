public class Registers {

    public int A = 0;
    public int B = 0;
    public int HL = 0;

    public void print() {

        System.out.println("A : " + Integer.toHexString(A).toUpperCase());
        System.out.println("B : " + Integer.toHexString(B).toUpperCase());
        System.out.println("HL: " + Integer.toHexString(HL).toUpperCase());
    }
}