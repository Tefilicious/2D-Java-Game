package main;

import javax.swing.JFrame;

public class Main {
    public static void main (String[] args) {

        JFrame window = new JFrame();
        // This lets the window properly close when user clicks the close button
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Make the window size fixed
        window.setResizable(false);
        window.setTitle("2D Adventure Game");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack(); // Causes this window to be sized to fit the preferred size and layouts of its subcomponents

        // Not specify the location of the window, window will be displayed at the center of the screen
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();

    }
}
