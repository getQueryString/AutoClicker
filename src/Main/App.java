// Copyright© by Fin

package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.util.concurrent.TimeUnit;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

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
    Thread startCounter;

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

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new GlobalKeyListener());
    }

    @Deprecated
    public App() {

        btn_stop.setEnabled(false);

        btn_start.addActionListener(e -> {

            if (!input.getText().isEmpty()) {
                try {
                    checkForInt = Integer.parseInt(input.getText());

                    if (!autoClickerRun) {

                        changeButtonText();
                        autoClickerRun = true;
                        btn_stop.setEnabled(true);
                        enableAutoClicker();
                    }
                } catch (NumberFormatException nfe) {
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

        btn_stop.addActionListener(e -> stopAutoClicker());
    }

    public void changeButtonText() {
        startCounter = new Thread(() -> {
            try {
                System.out.println("948rzhfuidn");
                btn_start.setText("Start (in 3s)");
                TimeUnit.SECONDS.sleep(1);
                btn_start.setText("Start (in 2s)");
                TimeUnit.SECONDS.sleep(1);
                btn_start.setText("Start (in 1s)");
                TimeUnit.SECONDS.sleep(1);
                btn_start.setText("Start (running)");
            } catch (InterruptedException ie) {

            }
        });
        startCounter.start();
    }

    @Deprecated
    public void enableAutoClicker() {

        acThread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
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
                    stopAutoClicker();
                }
            }
        });
        acThread.start();
    }

    @Deprecated
    public void stopAutoClicker() {
        if (acThread.isAlive()) {
            if (autoClickerRun) {
                autoClickerRun = false;
            }
            counter = 0;

            btn_start.setText("Start");
            btn_stop.setEnabled(false);

            acThread.stop();
        }
    }

    private static class GlobalKeyListener implements NativeKeyListener {

        public void nativeKeyPressed(NativeKeyEvent e) {
        /*if (e.getKeyCode() == KeyEvent.VK_F6) {
            System.out.println("F6 was pressed.");
        }*/
            System.out.println("Pressed: " + e.getKeyChar());
        }

        public void nativeKeyReleased(NativeKeyEvent e) {
            System.out.println("Released: " + e.getKeyChar());
        }

        public void nativeKeyTyped(NativeKeyEvent e) {
            System.out.println("Typed: " + e.getKeyChar());
        }
    }
}