// Copyright© by Fin

package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.util.concurrent.TimeUnit;

public class App {

    private JButton btn_start;
    private JPanel mainPanel;
    private JTextField textField;
    private JTextField input;

    private boolean autoClickerRun = false;
    private int counter = 0;
    private int checkForInt = 0;

    public static void main(String[] args) {
        JFrame jframe = new JFrame("AutoClicker - v1.0");

        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setResizable(false);
        jframe.setLocationRelativeTo(null);
        jframe.setAlwaysOnTop(true);
        jframe.setLayout(new GroupLayout(jframe));

        jframe.setContentPane(new App().mainPanel);

        jframe.pack();
        jframe.setVisible(true);
    }

    @Deprecated
    public App() {
        btn_start.addActionListener(e -> {
            if (!input.getText().isEmpty()) {
                try {
                    checkForInt = Integer.parseInt(input.getText());

                    if (!autoClickerRun) {
                        try {
                            TimeUnit.SECONDS.sleep(3);
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                        autoClickerRun = true;
                        while (autoClickerRun) {
                            try {
                                Robot robot = new Robot();
                                robot.mousePress(InputEvent.BUTTON1_MASK);
                                robot.mouseRelease(InputEvent.BUTTON1_MASK);
                                TimeUnit.MILLISECONDS.sleep(10);
                            } catch (AWTException | InterruptedException awtException) {
                                awtException.printStackTrace();
                            }
                            counter++;
                            if (counter == Integer.parseInt(input.getText())) {
                                autoClickerRun = false;
                                counter = 0;
                            }
                        }
                    }
                } catch (NumberFormatException numberformatexception) {
                    JOptionPane.showMessageDialog(null, "Eingabe muss eine Zahl sein!");
                    textField.setSelectedTextColor(Color.RED);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Zahl eintragen");
            }
        });
    }
}