public class Flags {

    boolean Z;
    boolean CY;
    boolean S;
    boolean P;

    public void update(int result) {
        int r = result & 0xFF;
        Z = (r == 0);
        S = (r & 0x80) != 0;
        P = Integer.bitCount(r) % 2 == 0;
    }

    public String toString() {
        return "Z=" + Z + " CY=" + CY + " S=" + S + " P=" + P;
    }
}