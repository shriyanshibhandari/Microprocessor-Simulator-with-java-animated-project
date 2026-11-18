public class Memory {

    private int[] memory;

    public Memory() {
        memory = new int[65536];
    }

    public void write(int address, int value) {
        memory[address] = value & 0xFF;
    }

    public int read(int address) {
        return memory[address];
    }
}