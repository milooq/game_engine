package com.company;

import com.company.drawing.Animator;
import com.company.physics.Engine;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
        //Меню и темы
        menu = new Menu(this.getContentPane());
        themeManager = new ThemeManager();
        themeManager.updateTheme("Default");
    }

     public class Menu {
        public Menu(Container C){
            c = C;
            startLocalGameButton = new JButton("Играть на одном компьютере");
            startLocalGameButton.setSize(width, height);
            startLocalGameButton.setLocation(cX - width/2, 200 - height/2);
            startLocalGameButton.setFocusable(false);
            startLocalGameButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    hide();
                    new Thread(Window.this::play).start();
                }
            });
            c.add(startLocalGameButton);
            c.repaint();
        }

        public void show(){
            startLocalGameButton.setVisible(true);
            c.repaint();
        }

        public void hide(){
            startLocalGameButton.setVisible(false);
        }

        private final JButton startLocalGameButton;
        private static final int width = 150, height = 50;
        Container c;
    }

    protected class ThemeManager{
        public ThemeManager(){

        }

        public void updateTheme(String name){
            Theme theme = new Theme(name);
            //текстуры
            leftPanel.setTheme(theme.panelTheme);
            rightPanel.setTheme(theme.panelTheme);
            ball.setTheme(theme.circleTheme);
            //задний фон
            a.setBackground(theme.backgroundTheme);
        }

        public class Theme{
            public Theme(String name){
                try {
                    panelTheme = ImageIO.read(new File(sourcesDirectory + name + "/panel.png"));
                    circleTheme = ImageIO.read(new File(sourcesDirectory+ name + "/ball.png"));
                backgroundTheme = ImageIO.read(new File(sourcesDirectory + name + "/background.png"));
//                buttonTheme = ImageIO.read(new File(name + "/panel.png"));
                }catch(IOException e) {
                    System.out.println("Ошибка загрузки темы!");
                }
            }
            protected BufferedImage panelTheme;
            protected BufferedImage circleTheme;
            protected BufferedImage backgroundTheme;
            protected BufferedImage buttonTheme;
            private static final String sourcesDirectory = "Themes/";
        }
    }

    public void play() {
        isPlaying = true;
        try {
            a.countDown(); // даем время приготовиться
            
            while (true){
                Thread.sleep(10);
                updateScore();
                if(checkWinner()){
                    isPlaying = false;
                    break;
                }

                e.update();
                a.draw();
            }
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        endGame();
    }

    private void endGame(){
        e.resetPanels();
        //reset score
        score[0] = score[1] = 0;
        menu.show();
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

    static boolean isPlaying = false;

    Menu menu;
    ThemeManager themeManager;
}
