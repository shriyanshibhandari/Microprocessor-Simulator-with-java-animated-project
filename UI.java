import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UI {

    public static void main(String[] args) {
        UIManager.put("Panel.background", new Color(28, 28, 28));
        UIManager.put("Label.foreground", new Color(235, 235, 235));
        UIManager.put("TitledBorder.titleColor", new Color(210, 210, 210));
        UIManager.put("ComboBox.background", new Color(45, 45, 45));
        UIManager.put("ComboBox.foreground", Color.WHITE);
        UIManager.put("Button.background", new Color(65, 65, 65));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("TextField.background", new Color(35, 35, 35));
        UIManager.put("TextField.foreground", Color.WHITE);
        UIManager.put("TextArea.background", new Color(25, 25, 25));
        UIManager.put("TextArea.foreground", new Color(230, 230, 230));
        UIManager.put("ScrollPane.background", new Color(28, 28, 28));
        UIManager.put("Spinner.background", new Color(35, 35, 35));
        UIManager.put("Spinner.foreground", Color.WHITE);

        JFrame frame = new JFrame("Microprocessor Simulator");
        frame.setSize(940, 760);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(12, 12));
        frame.getContentPane().setBackground(new Color(18, 18, 18));

        JLabel title = new JLabel("Microprocessor Simulator", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(235, 235, 235));
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setBorder(BorderFactory.createEmptyBorder(12, 12, 0, 12));
        frame.add(title, BorderLayout.NORTH);

        JTextArea inputArea = new JTextArea();
        inputArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        inputArea.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(80, 80, 80)), "Enter Instructions"));
        inputArea.setBackground(new Color(22, 22, 22));
        inputArea.setForeground(new Color(230, 230, 230));
        inputArea.setCaretColor(Color.WHITE);
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        inputArea.setRows(14);

        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        outputArea.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(80, 80, 80)), "Execution Log"));
        outputArea.setBackground(new Color(22, 22, 22));
        outputArea.setForeground(new Color(230, 230, 230));
        outputArea.setCaretColor(Color.WHITE);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        DefaultCaret caret = (DefaultCaret) outputArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        JScrollPane inputScroll = new JScrollPane(inputArea);
        inputScroll.setPreferredSize(new Dimension(560, 260));
        JScrollPane outputScroll = new JScrollPane(outputArea);
        outputScroll.setPreferredSize(new Dimension(900, 220));

        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.setBackground(new Color(28, 28, 28));
        leftPanel.add(inputScroll, BorderLayout.CENTER);

        JPanel examplePanel = new JPanel(new GridLayout(2, 2, 10, 10));
        examplePanel.setBackground(new Color(34, 34, 34));
        examplePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(80, 80, 80)), "Example Programs"));
        JComboBox<String> exampleBox = new JComboBox<>(new String[]{"Select Example", "Add No Carry", "Add With Carry", "Subtract With Borrow"});
        JButton loadExampleBtn = new JButton("Load Example");
        examplePanel.add(new JLabel("Choose example:"));
        examplePanel.add(exampleBox);
        examplePanel.add(new JLabel());
        examplePanel.add(loadExampleBtn);
        leftPanel.add(examplePanel, BorderLayout.SOUTH);

        JPanel regPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        regPanel.setBackground(new Color(34, 34, 34));
        regPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(80, 80, 80)), "Registers"));

        JTextField regA = createDisplayField();
        JTextField regB = createDisplayField();
        JTextField regHL = createDisplayField();

        regPanel.add(new JLabel("Register A:"));
        regPanel.add(regA);
        regPanel.add(new JLabel("Register B:"));
        regPanel.add(regB);
        regPanel.add(new JLabel("Register HL:"));
        regPanel.add(regHL);

        JPanel flagPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        flagPanel.setBackground(new Color(34, 34, 34));
        flagPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(80, 80, 80)), "Flags"));
        JTextField flagField = createDisplayField();
        flagField.setBackground(new Color(30, 30, 30));
        flagPanel.add(new JLabel("Current Flags:"));
        flagPanel.add(flagField);

        JPanel memPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        memPanel.setBackground(new Color(34, 34, 34));
        memPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(80, 80, 80)), "Input Memory"));
        JTextField mem1 = new JTextField();
        JTextField mem2 = new JTextField();
        memPanel.add(new JLabel("Memory[2500]:"));
        memPanel.add(mem1);
        memPanel.add(new JLabel("Memory[2501]:"));
        memPanel.add(mem2);

        JPanel statusPanel = new JPanel(new GridLayout(2, 1, 6, 6));
        statusPanel.setBackground(new Color(34, 34, 34));
        statusPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(80, 80, 80)), "Executor Status"));
        JLabel statusLabel = new JLabel("Ready to run instructions.");
        JLabel currentInstructionLabel = new JLabel("Current Instruction: -");
        statusPanel.add(statusLabel);
        statusPanel.add(currentInstructionLabel);

        JButton runBtn = new JButton("Run");
        JButton stepBtn = new JButton("Step");
        JButton pauseBtn = new JButton("Pause");
        JButton resumeBtn = new JButton("Resume");
        JButton stopBtn = new JButton("Stop");
        JCheckBox animateChk = new JCheckBox("Animate Run");
        SpinnerNumberModel delayModel = new SpinnerNumberModel(300, 50, 2000, 50);
        JSpinner delaySpinner = new JSpinner(delayModel);
        JButton clearBtn = new JButton("Clear");

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 10));
        btnPanel.setBackground(new Color(28, 28, 28));
        runBtn.setBackground(new Color(70, 70, 70));
        runBtn.setForeground(Color.WHITE);
        clearBtn.setBackground(new Color(70, 70, 70));
        clearBtn.setForeground(Color.WHITE);
        animateChk.setBackground(new Color(28, 28, 28));
        animateChk.setForeground(new Color(235, 235, 235));
        delaySpinner.setBackground(new Color(45, 45, 45));
        delaySpinner.setForeground(Color.WHITE);
        btnPanel.add(runBtn);
        btnPanel.add(stepBtn);
        btnPanel.add(pauseBtn);
        btnPanel.add(resumeBtn);
        btnPanel.add(stopBtn);
        btnPanel.add(animateChk);
        btnPanel.add(new JLabel("Delay (ms):"));
        btnPanel.add(delaySpinner);
        btnPanel.add(clearBtn);

        runBtn.setToolTipText("Run program (animated if 'Animate Run' checked)");
        stepBtn.setToolTipText("Execute one instruction (useful when paused)");
        pauseBtn.setToolTipText("Pause animated execution");
        resumeBtn.setToolTipText("Resume animated execution");
        stopBtn.setToolTipText("Stop animated execution");
        animateChk.setToolTipText("Play execution step-by-step with delay");
        delaySpinner.setToolTipText("Delay between steps when animated (ms)");

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));
        rightPanel.add(statusPanel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(regPanel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(flagPanel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(memPanel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(btnPanel);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setResizeWeight(0.68);
        splitPane.setBorder(null);

        frame.add(splitPane, BorderLayout.CENTER);
        frame.add(outputScroll, BorderLayout.SOUTH);

        loadExampleBtn.addActionListener(e -> {
            int index = exampleBox.getSelectedIndex();
            switch (index) {
                case 1:
                    inputArea.setText("LXI H 2500\nMOV A M\nINX H\nADD M\nINX H\nMOV M A\nHLT");
                    mem1.setText("0A");
                    mem2.setText("05");
                    statusLabel.setText("Example loaded: Add No Carry");
                    break;
                case 2:
                    inputArea.setText("LXI H 2500\nMOV A M\nINX H\nADD M\nINX H\nMOV M A\nHLT");
                    mem1.setText("FF");
                    mem2.setText("01");
                    statusLabel.setText("Example loaded: Add With Carry");
                    break;
                case 3:
                    inputArea.setText("LXI H 2500\nMOV A M\nINX H\nSUB M\nINX H\nMOV M A\nHLT");
                    mem1.setText("05");
                    mem2.setText("0A");
                    statusLabel.setText("Example loaded: Subtract With Borrow");
                    break;
                default:
                    statusLabel.setText("Select an example to load.");
            }
        });

        final InstructionSet.ExecutionController[] controllerHolder = new InstructionSet.ExecutionController[1];

        runBtn.addActionListener(e -> {
            CPU cpu = new CPU();
            outputArea.setText("");
            currentInstructionLabel.setText("Current Instruction: -");
            statusLabel.setText("Running...");

            try {
                String text = inputArea.getText();
                String[] lines = text.split("\n");
                List<String> program = new ArrayList<>();
                for (String line : lines) {
                    if (!line.trim().isEmpty()) {
                        program.add(line.trim());
                    }
                }

                int val1 = parseInput(mem1.getText());
                int val2 = parseInput(mem2.getText());
                cpu.memory.write(0x2500, val1);
                cpu.memory.write(0x2501, val2);

                Runnable updateUI = () -> {
                    animateRegisterUpdate(regA);
                    animateRegisterUpdate(regB);
                    animateRegisterUpdate(regHL);
                    regA.setText(formatHex(cpu.reg.A));
                    regB.setText(formatHex(cpu.reg.B));
                    regHL.setText(formatHex(cpu.reg.HL));
                    flagField.setText(cpu.flags != null ? cpu.flags.toString() : "N/A");
                };

                if (animateChk.isSelected()) {
                    int delay = (Integer) delaySpinner.getValue();
                    InstructionSet.ExecutionController controller = new InstructionSet.ExecutionController();
                    controllerHolder[0] = controller;
                    
                    new Thread(() -> {
                        InstructionSet.executeInteractive(program, cpu, delay, pc -> SwingUtilities.invokeLater(() -> {
                            String step = pc > 0 && pc <= program.size() ? program.get(Math.max(0, pc - 1)) : "Done";
                            currentInstructionLabel.setText("Current Instruction: " + step);
                            outputArea.append("[STEP] PC=" + pc + " | " + step + "\n");
                            outputArea.append("  A=" + formatHex(cpu.reg.A) + "  B=" + formatHex(cpu.reg.B) + "  HL=" + formatHex(cpu.reg.HL) + "\n");
                            outputArea.append("  Flags=" + (cpu.flags != null ? cpu.flags.toString() : "N/A") + "\n");
                            outputArea.append("  Memory[2502]=" + formatHex(cpu.memory.read(0x2502)) + "\n");
                            outputArea.append("------------------------------------------\n");
                            updateUI.run();
                        }), controller);
                    }).start();
                } else {
                    InstructionSet.execute(program, cpu);
                    updateUI.run();
                    statusLabel.setText("Execution completed.");
                    outputArea.append("STATUS: Execution completed\n");
                    outputArea.append("------------------------------------------\n");
                    outputArea.append("Registers:\n");
                    outputArea.append("  A  = " + formatHex(cpu.reg.A) + "\n");
                    outputArea.append("  B  = " + formatHex(cpu.reg.B) + "\n");
                    outputArea.append("  HL = " + formatHex(cpu.reg.HL) + "\n");
                    outputArea.append("Flags: " + (cpu.flags != null ? cpu.flags.toString() : "N/A") + "\n");
                    outputArea.append("Memory[2502] = " + formatHex(cpu.memory.read(0x2502)) + "\n");
                    outputArea.append("Memory[2503] = " + formatHex(cpu.memory.read(0x2503)) + "\n");
                }
            } catch (Exception ex) {
                statusLabel.setText("Error: Invalid memory input");
                outputArea.setText("ERROR: Enter valid decimal or hex values for memory inputs.\n");
            }
        });

        clearBtn.addActionListener(e -> {
            inputArea.setText("");
            outputArea.setText("");
            regA.setText("");
            regB.setText("");
            regHL.setText("");
            flagField.setText("");
            mem1.setText("");
            mem2.setText("");
            statusLabel.setText("Ready to run instructions.");
            currentInstructionLabel.setText("Current Instruction: -");
            exampleBox.setSelectedIndex(0);
        });

        stepBtn.addActionListener(e -> {
            InstructionSet.ExecutionController c = controllerHolder[0];
            if (c != null) c.requestStep();
        });

        pauseBtn.addActionListener(e -> {
            InstructionSet.ExecutionController c = controllerHolder[0];
            if (c != null) c.pause();
        });

        resumeBtn.addActionListener(e -> {
            InstructionSet.ExecutionController c = controllerHolder[0];
            if (c != null) c.resume();
        });

        stopBtn.addActionListener(e -> {
            InstructionSet.ExecutionController c = controllerHolder[0];
            if (c != null) c.stop();
        });

        frame.setVisible(true);
    }

    private static JTextField createDisplayField() {
        JTextField field = new JTextField();
        field.setEditable(false);
        field.setBackground(new Color(18,18,18));
        field.setForeground(new Color(120, 220, 140));
        field.setFont(new Font("Consolas", Font.BOLD, 14));
        return field;
    }

    private static void animateRegisterUpdate(JTextField field) {
        Color orig = field.getBackground();
        field.setBackground(new Color(60, 60, 60));
        Timer t = new Timer(200, null);
        final int[] cnt = {0};
        t.addActionListener(e -> {
            cnt[0]++;
            if (cnt[0] >= 2) {
                field.setBackground(orig);
                t.stop();
            }
        });
        t.setRepeats(true);
        t.start();
    }

    private static String formatHex(int value) {
        return "0x" + String.format("%02X", value & 0xFF);
    }

    private static int parseInput(String input) {
        input = input.trim().toUpperCase();
        if (input.isEmpty()) {
            return 0;
        }
        if (input.matches(".*[A-F].*")) {
            return Integer.parseInt(input, 16);
        } else {
            return Integer.parseInt(input);
        }
    }
}
