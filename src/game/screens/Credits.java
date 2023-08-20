package game.screens;

import game.gameplay.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Credits extends JPanel implements ActionListener {
    JFrame window;
    Timer timer = new Timer(20, this);

    String text = "";
    String tip = "";
    int textY = 400;
    int ending;

    public Credits(int ending, String tip) {
        this.ending = ending;
        this.tip = tip;

        System.setProperty("sun.java2d.opengl", "true");
        SwingUtilities.invokeLater(this::rollingCredits);
    }

    public Credits(int ending) {
        this.ending = ending;

        System.setProperty("sun.java2d.opengl", "true");
        SwingUtilities.invokeLater(this::rollingCredits);
    }

    public Credits(String text) {//Custom ending first line
        this.text = text;
        ending = 999;//doesn't need automatic text

        System.setProperty("sun.java2d.opengl", "true");
        SwingUtilities.invokeLater(this::rollingCredits);
    }

    private void rollingCredits() {//Displays the Credits scene
        window = new JFrame("Credits");
        window.add(this);
        window.setResizable(false);
        window.setSize(600, 400);
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        switch (ending) {//unique message
            case 1 -> text = "You finished the Normal ENDING";//first ending
            case 2 -> text = "\"F\"\n\n" + tip;//Any death ending
            case 999 -> text += "";//custom message

            default -> text = "You finished ENDING: " + ending;
        }

        this.text += "\n\n\n\"A Normal Clicker\"\n\n\n" +
                "DEVELOPER:               \n" +
                "Manu\n\n\n" +
                "TESTER:                      \n" +
                "Dani\n\n\n\n\n" +
                "Thank you for playing!";

        repaint();

        Player player = new Player();
        player.save();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d =  (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setFont(new Font("Montserrat", Font.PLAIN, 26));

        int y = textY;

        for(String line : text.split("\n")) {
            int stringLength = (int) g2d.getFontMetrics().getStringBounds(line, g2d).getWidth();
            int x = (getWidth() - stringLength) / 2;

            g2d.drawString(line, x, y += 28);
        }
        timer.start();
    }

    public void actionPerformed(ActionEvent e) {

        if(textY <= -480) {
            timer.stop();
            window.dispose();
        }

        textY--;
        repaint();
    }
}