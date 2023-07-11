package game.view.gui;

import java.awt.*;

import javax.swing.*;

public class WelcomePanel extends JPanel {
    private final JLabel welcomeLabel;
    private JLabel creditLabel;

    public WelcomePanel() {
        this.welcomeLabel = new JLabel("Welcome to Doctor Lucky's Mansion");
        this.creditLabel = new JLabel("Credits: ");
        this.setLayout(new BorderLayout());
        this.add(this.welcomeLabel, BorderLayout.CENTER);
        this.add(this.creditLabel, BorderLayout.SOUTH);
    }

    public void makeVisible() {
        this.setVisible(true);
    }
}
