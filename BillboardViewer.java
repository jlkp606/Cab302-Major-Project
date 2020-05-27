package com.company;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.event.*;

public class BillboardViewer {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Billboard Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        MouseListener mouseexit = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                    frame.dispose();
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
            }
        };
        KeyListener escapeexit = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    frame.dispose();
                }
            }
        };
        frame.addMouseListener(mouseexit);
        frame.addKeyListener(escapeexit);
        frame.getContentPane().add(new JLabel(" HEY!!!"));
        frame.setUndecorated(true);
        frame.setVisible(true);
        frame.setFocusable(true);
    }
}
