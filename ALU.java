public class ALU {

    private CPU cpu;

    public ALU(CPU cpu) {
        this.cpu = cpu;
    }

    public void add(int value) {

        int a = cpu.reg.A & 0xFF;
        int b = value & 0xFF;

        int sum = a + b;

        boolean c = (sum > 0xFF);
        if (cpu.flags != null) cpu.flags.CY = c;

        cpu.reg.A = sum & 0xFF;
        if (cpu.flags != null) cpu.flags.update(sum);
    }

    public void sub(int value) {
        int a = cpu.reg.A & 0xFF;
        int b = value & 0xFF;

        int result = a - b;

        boolean borrow = (a < b);
        if (cpu.flags != null) cpu.flags.CY = borrow;

        cpu.reg.A = result & 0xFF;
        if (cpu.flags != null) cpu.flags.update(result);
    }
}