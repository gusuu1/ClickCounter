package space.guus.clickcounter;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class Main implements ActionListener {

    private int lastClicks = 0;
    private int clicks = 0;
    private JLabel label = new JLabel("Number of click: 0");
    private JLabel label2 = new JLabel("Clicks per second: 0");
    private JFrame frame = new JFrame();

    public Main() {
        // create a button
        JButton button = new JButton("Click me!");
        button.addActionListener(this);

        // panel with button and label
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(button);
        panel.add(label);
        panel.add(label2);

        // setup the frame and display it
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Click Counter!");
        frame.pack();
        frame.setVisible(true);
        cpsCounter();
    }

    private void cpsCounter(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                int cps = clicks - lastClicks;
                label2.setText("Clicks per second: " + cps);
                lastClicks = clicks;
            }
        }, 0L, 1000L);
    }

    // click handler
    public void actionPerformed(ActionEvent e){
        clicks++;
        label.setText("Number of clicks: " + clicks);

        URL url = this.getClass().getClassLoader().getResource("horn.wav");
        try {
            AudioInputStream sound = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(sound);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
        System.out.println(clicks);
    }

    // main, create frame
    public static void main(String[] args) {
        new Main();
    }
}
