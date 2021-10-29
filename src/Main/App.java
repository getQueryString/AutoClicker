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
    private JButton btn_stop;

    private boolean autoClickerRun = false;
    private int counter = 0;
    private int checkForInt = 0;

    Thread acThread;

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
                        enableAutoClicker();
                    }
                } catch (NumberFormatException numberformatexception) {
                    JOptionPane optionPane = new JOptionPane("Eingabe muss eine Zahl sein!", JOptionPane.ERROR_MESSAGE);
                    JDialog dialog = optionPane.createDialog("Eingabefehler");
                    dialog.setAlwaysOnTop(true);
                    dialog.setVisible(true);
                    textField.setSelectedTextColor(Color.RED);
                }
            } else {
                JOptionPane optionPane = new JOptionPane("Zahl eintragen", JOptionPane.ERROR_MESSAGE);
                JDialog dialog = optionPane.createDialog("Eingabefehler");
                dialog.setAlwaysOnTop(true);
                dialog.setVisible(true);
            }
        });

        btn_stop.addActionListener(e -> {
            if (autoClickerRun) {
                autoClickerRun = false;
                try {
                    if (acThread.isAlive()) {
                        acThread.join();
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    @Deprecated
    public void enableAutoClicker() {
        acThread = new Thread(() -> {
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
        });
        acThread.start();
    }
}