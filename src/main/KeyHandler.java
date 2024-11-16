package main;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class KeyHandler implements KeyListener {
    // KeyListener listens for a key stroke event

    public boolean upPressed, downPressed, leftPressed, rightPressed;

    @Override
    public void keyTyped (KeyEvent e) {
    }

    @Override
    public void keyPressed (KeyEvent e) {

        int code = e.getKeyCode();

        // VK = Virtual Key code, reports which keyboard key has been pressed
        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }

    }

    @Override
    public void keyReleased (KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }


}
