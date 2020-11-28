package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Window extends JFrame {

    public static int width = 1000;
    public static int height = 530;

    Animator a;

    public Window() {

        this.setVisible(true);
        this.setTitle("Game");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(0, 0, width, height);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
//      add panel to main frame

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    a.moveRUp();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    a.moveRDown();
                }

                if(e.getKeyCode() == KeyEvent.VK_W){
                    a.moveLUp();
                }
                if(e.getKeyCode() == KeyEvent.VK_S){
                    a.moveLDown();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN){
                    a.stopR();
                }
                if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S){
                    a.stopL();
                }
            }
        });

        Graphics g = this.getGraphics();
        try {
            a = new Animator(g);
        } catch (NullPointerException e) {
            System.out.println("Шарик, ты балбес");
        }
        a.start();
    }

        @Override
        public void paint (Graphics g){
            super.paint(g);
        }
}
