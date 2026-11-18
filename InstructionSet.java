import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.function.Consumer;

public class InstructionSet {

    public static class ExecutionController {
        private boolean paused = false;
        private boolean stepRequested = false;
        private boolean stopped = false;

        public synchronized void pause() { paused = true; }
        public synchronized void resume() { paused = false; stepRequested = false; notifyAll(); }
        public synchronized void requestStep() { stepRequested = true; paused = false; notifyAll(); }
        public synchronized void stop() { stopped = true; notifyAll(); }
        public synchronized boolean isStopped() { return stopped; }

        public synchronized void waitWhilePaused() {
            while (paused && !stepRequested && !stopped) {
                try { wait(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); break; }
            }
        }

        public synchronized boolean consumeStepIfRequested() {
            if (stepRequested) { stepRequested = false; paused = true; return true; }
            return false;
        }
    }

    public static void execute(List<String> program, CPU cpu) {
        Map<String, Integer> labels = buildLabelMap(program);

        int pc = 0;

        while (pc < program.size()) {
            String line = program.get(pc).trim();
            if (line.isEmpty() || line.endsWith(":")) {
                pc++;
                continue;
            }
            String[] parts = line.split("\\s+");
            String instruction = parts[0];
            switch (instruction) {
                case "LXI":
                    if (parts[1].equals("H")) {
                        cpu.reg.HL = Integer.parseInt(parts[2], 16);
                    }
                    pc++;
                    break;
                case "MVI":
                    if (parts[1].equals("B")) {
                        cpu.reg.B = Integer.parseInt(parts[2], 16);
                        if (cpu.flags != null) cpu.flags.update(cpu.reg.B);
                    }
                    pc++;
                    break;
                case "MOV":
                    if (parts[1].equals("A") && parts[2].equals("M")) {
                        cpu.reg.A = cpu.memory.read(cpu.reg.HL);
                        if (cpu.flags != null) cpu.flags.update(cpu.reg.A);
                    } else if (parts[1].equals("M") && parts[2].equals("A")) {
                        cpu.memory.write(cpu.reg.HL, cpu.reg.A);
                    } else if (parts[1].equals("M") && parts[2].equals("B")) {
                        cpu.memory.write(cpu.reg.HL, cpu.reg.B);
                    }
                    pc++;
                    break;
                case "INX":
                    if (parts[1].equals("H")) {
                        cpu.reg.HL++;
                    }
                    pc++;
                    break;
                case "ADD":
                    if (parts[1].equals("M")) {
                        int value = cpu.memory.read(cpu.reg.HL);
                        cpu.alu.add(value);
                    }
                    pc++;
                    break;
                case "SUB":
                    if (parts[1].equals("M")) {
                        int value = cpu.memory.read(cpu.reg.HL);
                        cpu.alu.sub(value);
                    }
                    pc++;
                    break;
                case "JNC":
                    String target = parts[1];
                    int dest = -1;
                    try {
                        dest = Integer.parseInt(target);
                    } catch (NumberFormatException nfe) {
                        if (labels.containsKey(target)) dest = labels.get(target);
                    }
                    boolean carry = cpu.flags != null ? cpu.flags.CY : false;
                    if (!carry && dest >= 0) {
                        pc = dest;
                    } else {
                        pc++;
                    }
                    break;
                case "INR":
                    if (parts[1].equals("B")) {
                        cpu.reg.B = (cpu.reg.B + 1) & 0xFF;
                        if (cpu.flags != null) {
                            cpu.flags.update(cpu.reg.B);
                            cpu.flags.CY = false;
                        }
                    }
                    pc++;
                    break;
                case "HLT":
                    System.out.println("Program Halted");
                    return;
                default:
                    System.out.println("Unknown instruction: " + line);
                    pc++;
            }
        }
    }

    private static Map<String, Integer> buildLabelMap(List<String> program) {
        Map<String, Integer> labels = new HashMap<>();
        for (int i = 0; i < program.size(); i++) {
            String l = program.get(i).trim();
            if (l.endsWith(":")) {
                String label = l.substring(0, l.length() - 1);
                labels.put(label, i + 1);
            } else if (l.contains(":")) {
                int idx = l.indexOf(":");
                String label = l.substring(0, idx);
                labels.put(label, i);
            }
        }
        return labels;
    }

    public static void executeInteractive(List<String> program, CPU cpu, int delayMs, Consumer<Integer> stepCallback, ExecutionController controller) {
        Map<String, Integer> labels = buildLabelMap(program);

        int pc = 0;
        while (pc < program.size()) {
            String line = program.get(pc).trim();
            if (line.isEmpty() || line.endsWith(":")) {
                pc++;
                continue;
            }

            if (controller != null) {
                controller.waitWhilePaused();
                if (controller.isStopped()) return;
            }

            String[] parts = line.split("\\s+");
            String instruction = parts[0];

            switch (instruction) {
                case "LXI":
                    if (parts[1].equals("H")) {
                        cpu.reg.HL = Integer.parseInt(parts[2], 16);
                    }
                    pc++;
                    break;

                case "MVI":
                    if (parts[1].equals("B")) {
                        cpu.reg.B = Integer.parseInt(parts[2], 16);
                        if (cpu.flags != null) cpu.flags.update(cpu.reg.B);
                    }
                    pc++;
                    break;

                case "MOV":
                    if (parts[1].equals("A") && parts[2].equals("M")) {
                        cpu.reg.A = cpu.memory.read(cpu.reg.HL);
                        if (cpu.flags != null) cpu.flags.update(cpu.reg.A);
                    } else if (parts[1].equals("M") && parts[2].equals("A")) {
                        cpu.memory.write(cpu.reg.HL, cpu.reg.A);
                    } else if (parts[1].equals("M") && parts[2].equals("B")) {
                        cpu.memory.write(cpu.reg.HL, cpu.reg.B);
                    }
                    pc++;
                    break;

                case "INX":
                    if (parts[1].equals("H")) {
                        cpu.reg.HL++;
                    }
                    pc++;
                    break;

                case "ADD":
                    if (parts[1].equals("M")) {
                        int value = cpu.memory.read(cpu.reg.HL);
                        cpu.alu.add(value);
                    }
                    pc++;
                    break;

                case "SUB":
                    if (parts[1].equals("M")) {
                        int value = cpu.memory.read(cpu.reg.HL);
                        cpu.alu.sub(value);
                    }
                    pc++;
                    break;

                case "JNC":
                    String target = parts[1];
                    int dest = -1;
                    try {
                        dest = Integer.parseInt(target);
                    } catch (NumberFormatException nfe) {
                        if (labels.containsKey(target)) dest = labels.get(target);
                    }
                    boolean carry = cpu.flags != null ? cpu.flags.CY : false;
                    if (!carry && dest >= 0) {
                        pc = dest;
                    } else {
                        pc++;
                    }
                    break;

                case "INR":
                    if (parts[1].equals("B")) {
                        cpu.reg.B = (cpu.reg.B + 1) & 0xFF;
                        if (cpu.flags != null) {
                            cpu.flags.update(cpu.reg.B);
                            cpu.flags.CY = false;
                        }
                    }
                    pc++;
                    break;

                case "HLT":
                    System.out.println("Program Halted");
                    if (stepCallback != null) stepCallback.accept(pc);
                    return;

                default:
                    System.out.println("Unknown instruction: " + line);
                    pc++;
            }

            if (stepCallback != null) stepCallback.accept(pc);

            if (controller != null) {
                if (controller.consumeStepIfRequested()) {
                }
                if (controller.isStopped()) return;
            }

            try {
                Thread.sleep(delayMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
