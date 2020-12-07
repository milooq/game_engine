package com.company;

import com.company.drawing.Animator;
import com.company.physics.Engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.atomic.AtomicBoolean;


public class Window extends JFrame {

    public static int width = 1000;
    public static int height = 530;

    public static final int cX = width/2;
    public static final int cY = height/2;

    public Window() {
        this.setVisible(true);
        this.setTitle("Game");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(0, 0, width, height);
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    moveRightPanelUp();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    moveRightPanelDown();
                }

                if(e.getKeyCode() == KeyEvent.VK_W){
                    moveLeftPanelUp();
                }
                if(e.getKeyCode() == KeyEvent.VK_S){
                    moveLeftPanelDown();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN){
                    stopRightPanel();
                }
                if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S){
                    stopLeftPanel();
                }
            }
        });

        //Создаем игровые объекты
        ball = new Ball(new Vec2(0, 0), 40, Vec2.mul(Vec2.randomVec(3, 7),Vec2.randomDirection()));
        leftPanel = new Panel(panelWidth, panelHeight, new Vec2(-cX + 20, 50), new Vec2(0, 0));
        rightPanel = new Panel(panelWidth, panelHeight, new Vec2(cX - 40, 50), new Vec2(0, 0));

        //Настройка физики
        this.e = new Engine(leftPanel, rightPanel, ball);
        //Настройка отрисовки
        this.a = new Animator(this.getGraphics());
        this.a.addDrawable(ball); this.a.addDrawable(leftPanel); this.a.addDrawable(rightPanel);
        menu = new Menu(this.getContentPane());
    }

//  Может быть стоит поместить окно внутри меню?
//  Так всё было бы намного проще
    class Menu {
        public Menu(Container c){
            startLocalGameButton = new JButton("Играть на одном компьютере");
            startLocalGameButton.setSize(width, height);
            startLocalGameButton.setLocation(cX - width/2, 200 - height/2);
            startLocalGameButton.setFocusable(false);
            startLocalGameButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    startLocalGameButton.setVisible(false);
                    startGame.set(true);
                }
            });
            c.add(startLocalGameButton);
            c.repaint();
        }

        private final JButton startLocalGameButton;
        private static final int width = 150, height = 50;
    }

    public void play() throws InterruptedException {
        while(!startGame.get()){
//            Ждём пока не нажали кнопку играть
        }
        a.countDown(); // даем время приготовиться
        while (true){
            Thread.sleep(10);
            updateScore();
            if(checkWinner()){
                break;
            }

            e.update();
            a.draw();
        }
    }

    private void updateScore(){
        Ball.pp res = ball.playerPoint();
        if(res != Ball.pp.NONE){
            Window.score[res.ordinal()] += 1;
            e.setNewBall();
        }
    }

    private void moveLeftPanelUp(){
        leftPanel.setVelocity(new Vec2(0, panelSpeed));
    }
    private void moveLeftPanelDown(){
        leftPanel.setVelocity(new Vec2(0, -panelSpeed));
    }
    private void stopLeftPanel(){
        leftPanel.setVelocity(new Vec2(0,0));
    }

    private void moveRightPanelUp(){
        rightPanel.setVelocity(new Vec2(0, panelSpeed));
    }
    private void moveRightPanelDown(){
        rightPanel.setVelocity(new Vec2(0, -panelSpeed));
    }
    private void stopRightPanel(){
        rightPanel.setVelocity(new Vec2(0,0));
    }

    private boolean checkWinner() {
        boolean winnerLeft = Window.score[Ball.pp.LEFT.ordinal()] == 10;
        boolean winnerRight = Window.score[Ball.pp.RIGHT.ordinal()] == 10;
        if(winnerLeft || winnerRight){
            JOptionPane.showMessageDialog(null, "Победил " + ((winnerLeft) ? "левый" : "правый") + " игрок");
            score[0] = score[1] = 0;
            return true;
        }
        return false;
    }

    static public int[] score = {0, 0};

    //View
    Animator a;
    //Model
    Engine e;

    //Objects
    Ball ball;

    Panel leftPanel, rightPanel;
    private final int panelSpeed = 5;
    private static final int panelWidth = 20;
    private static final int panelHeight = 80;

    Menu menu;
    //Atomic чтобы обновлять и считывать переменную из разных потоков
    AtomicBoolean startGame = new AtomicBoolean(false);
}
