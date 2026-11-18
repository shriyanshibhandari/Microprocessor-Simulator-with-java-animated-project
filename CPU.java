import java.util.*;

public class CPU {

    public Registers reg;
    public Memory memory;
    public Flags flags;

    private List<String> program;
    private Scanner sc;
    public ALU alu;

    public CPU() {
    reg = new Registers();
    memory = new Memory();
    program = new ArrayList<>();
    sc = new Scanner(System.in);
    flags = new Flags();
    alu = new ALU(this);
}

    public void loadProgram() {

        System.out.println("Enter number of instructions:");
        int n = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter instructions:");

        for (int i = 0; i < n; i++) {
            program.add(sc.nextLine());
        }
    }

    public void takeUserInputForMemory() {

        System.out.println("Enter first number (Memory 2500) in HEX:");
        String val1 = sc.next();
        memory.write(0x2500, Integer.parseInt(val1, 16));

        System.out.println("Enter second number (Memory 2501) in HEX:");
        String val2 = sc.next();
        memory.write(0x2501, Integer.parseInt(val2, 16));
    }

    public void run() {

        InstructionSet.execute(program, this);

        System.out.println("\nExecution Completed");

        System.out.println("\nRegisters:");
        reg.print();

        System.out.println("\nMemory Output:");

        System.out.println("2502 = " +
                Integer.toHexString(memory.read(0x2502)).toUpperCase());

        System.out.println("2503 = " +
                Integer.toHexString(memory.read(0x2503)).toUpperCase());
    }
}