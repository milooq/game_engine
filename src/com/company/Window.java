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
import java.io.FilenameFilter;
import java.io.IOException;


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
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    int winner = (score[0] >= score[1]) ? 0 : 1;
                    score[winner] = 10;
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
        themeManager = new ThemeManager();
        themeManager.updateTheme("Default");
        menuManager = new MenuManager(this);
    }

    public class MenuManager{
        public MenuManager(Container c){
            mm = new MainMenu(c);
            tm = new ThemeMenu(c);
            current = mm;
        }

        public abstract class Menu {
            abstract void show();
            abstract void hide();
            protected JPanel env;
        }

        void displayMainMenu(){
            current.hide();
            mm.show();
            current = mm;
        }

        void displayThemeMenu(){
            current.hide();
            tm.show();
            current = tm;
        }

        public class MainMenu extends Menu {
            public MainMenu(Container c){
                this.c = c;
                env = new JPanel(null);
                env.setBounds(0,0,Window.width,Window.height);
                JButton gameButton = new JButton("Играть на одном компьютере");
                gameButton.setSize(width, height);
                gameButton.setLocation(cX - width/2, 200 - height/2);
                gameButton.setFocusable(false);
                gameButton.addActionListener(e -> {
                    hide();
                    new Thread(Window.this::play).start();
                });

                JButton themeMenu = new JButton("Темы");
                themeMenu.setSize(width, height);
                themeMenu.setLocation(cX - width/2, 300 - height/2);
                themeMenu.setFocusable(false);
                themeMenu.addActionListener(e -> displayThemeMenu());

                JButton exit = new JButton("Выход");
                exit.setSize(width, height);
                exit.setLocation(cX - width/2, 400 - height/2);
                exit.setFocusable(false);
                exit.addActionListener(e -> exit());

                env.add(gameButton);
                env.add(themeMenu);
                env.add(exit);

                c.add(env);
                c.validate();
                c.repaint();
            }
            @Override
            public void show(){
                env.setVisible(true);
                c.repaint();
            }
            @Override
            public void hide(){
                env.setVisible(false);
            }

            private static final int width = 150, height = 50;
            Container c;
        }

        public class ThemeMenu extends Menu {
            public ThemeMenu(Container c){
                this.c = c;
                env = new JPanel(null);
                env.setBounds(0,0,Window.width,Window.height);
                env.setVisible(false);

                JButton back = new JButton("Назад");
                back.setFocusable(false);
                back.setSize(width, height);
                back.setLocation(cX - width/2, Window.height - 75 - height);
                back.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        displayMainMenu();
                    }
                });

                File file = new File("Themes");
                String[] themes = file.list((current, name) -> new File(current, name).isDirectory());
                int posX = 0, posY = 0;
                for(String theme : themes){
                    JButton button = new JButton(theme);
                    button.setSize(Window.width/3, Window.height - 150);
                    button.setLocation(posX, posY);
                    button.setFocusable(false);
                    button.addActionListener(e -> {
                        System.out.println("Выбрана тема "+ theme);
                        themeManager.updateTheme(theme);
                    });
                    button.setIcon(new ImageIcon(themeManager.getPreview(theme, Window.width/3+10, Window.height - 150)));
                    env.add(button);
                    posX += Window.width/3;
                }

                env.add(back);

                c.add(env);
                c.validate();
                c.repaint();
            }
            @Override
            public void show(){
                System.out.println("Вывожу меню с темами");
                env.setVisible(true);
                c.repaint();
            }
            @Override
            public void hide(){
                System.out.println("Скрываю меню с темами");
                env.setVisible(false);
            }

            private static final int width = 150, height = 50;
            Container c;
        }

        MainMenu mm;
        ThemeMenu tm;
        Menu current;
    }

    protected class ThemeManager{
        public ThemeManager(){
//            pass
        }

        public BufferedImage getPreview(String name, int width, int height){
            BufferedImage i = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
            Theme theme = new Theme(name);
            i.getGraphics().drawImage(theme.backgroundTheme, 0,0, width, height, null);
            i.getGraphics().drawImage(theme.panelTheme, 50,50,20 ,80, null);
            i.getGraphics().drawImage(theme.ballTheme, width/2 - 20,height/2 -20, 40, 40, null);
            return i;
        }

        public void updateTheme(String name){
            Theme theme = new Theme(name);
            //текстуры
            leftPanel.setTheme(theme.panelTheme);
            rightPanel.setTheme(theme.panelTheme);
            ball.setTheme(theme.ballTheme);
            //задний фон
            a.setBackground(theme.backgroundTheme);
        }

        public class Theme{
            public Theme(String name){
                try {
                    panelTheme = ImageIO.read(new File(sourcesDirectory + name + "/panel.png"));
                    ballTheme = ImageIO.read(new File(sourcesDirectory+ name + "/ball.png"));
                    backgroundTheme = ImageIO.read(new File(sourcesDirectory + name + "/background.png"));
//                buttonTheme = ImageIO.read(new File(name + "/panel.png"));
                }catch(IOException e) {
                    System.out.println("Ошибка загрузки темы!");
                }
            }
            protected BufferedImage panelTheme;
            protected BufferedImage ballTheme;
            protected BufferedImage backgroundTheme;
            protected BufferedImage buttonTheme;
            private static final String sourcesDirectory = "Themes/";
        }
    }

    public void exit(){
        this.dispose();
    }

    public void play() {
        try {
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
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        endGame();
    }

    private void endGame(){
        e.resetPanels();
        //reset score
        score[0] = score[1] = 0;
        menuManager.displayMainMenu();
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

    MenuManager menuManager;
    ThemeManager themeManager;
}