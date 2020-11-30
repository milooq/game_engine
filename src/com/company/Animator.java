package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class Animator extends Thread{

    public Animator(Graphics g) {
        //Настройки графики
        this.g = g;
        frame = new BufferedImage(Window.width,Window.height,BufferedImage.TYPE_INT_RGB);
        frameGraphics = frame.getGraphics();
        frameGraphics.setFont(new Font("TimesRoman", Font.PLAIN, 50));

        //Создаем игровые объекты
        b = new Ball(new Vec2(0, 0), 40, Vec2.mul(randomVec(3, 7),randomDirection()));
        leftPanel = new Panel(panelWidth, 100, new Vec2(-cX + 20, 50), new Vec2(0, 0));
        rightPanel = new Panel(panelWidth, 100, new Vec2(cX - 40, 50), new Vec2(0, 0));
}

    public void clear(){
        frameGraphics.setColor(Color.white);
        frameGraphics.fillRect(0,0,Window.width,Window.height);
        frameGraphics.setColor(Color.black);
    }

    // старые в новые
    static public Vec2 CoordBadToNormal(Vec2 v){
        return new Vec2(v.getX() - cX, -v.getY() + cY);
    }

    // новые в старые
    static public Vec2 CoordNormalToBad(Vec2 v){
        return new Vec2(v.getX() + cX, -v.getY() + cY);
    }

    static public Vec2 VelocityConvert(Vec2 v){
        return new Vec2(v.getX(), -v.getY());
    }

//    @Override
//    public void run() {
//        while (true){
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            clear();
//            CheckCollisions();
//            displayDelimiter();
//            updateScore();
//            b.update();
//            leftPanel.update();
//            rightPanel.update();
//            b.draw(frameGraphics);
//            leftPanel.draw(frameGraphics);
//            rightPanel.draw(frameGraphics);
//            g.drawImage(frame,0,0, Window.width, Window.height,null);
//        }
//    }

    @Override
    public void run() {
        final float fps = 60;
        final float dt = 1 / fps;
        float accumulator = 0;

        // Единицы измерения - секунды
        //float frameStart = GetCurrentTime( )
        double frameStarts = System.currentTimeMillis() / 1000.;

        // основной цикл
        while (true) {
            final double currentTime = System.currentTimeMillis() / 1000.;

            // Сохраняется время, прошедшее с начала последнего кадра
            accumulator += currentTime - frameStarts;

            // Записывается начало этого кадра
            frameStarts = currentTime;

            while (accumulator > dt) {
                //UpdatePhysics
                CheckCollisions();
                b.update();
                leftPanel.update();
                rightPanel.update();
                accumulator -= dt;
            }

            //RenderGame
            clear();
            updateScore();
            displayDelimiter();
            b.draw(frameGraphics);
            leftPanel.draw(frameGraphics);
            rightPanel.draw(frameGraphics);
            g.drawImage(frame,0,0, Window.width, Window.height,null);
        }
    }

    private void updateScore(){
            Ball.eOutOfBounce res = b.OutOfBonce();
            switch (res) {
                case RIGHT:
                    scoreLeft +=1;
                    this.b = new Ball(new Vec2(0, 0), 40, Vec2.mul(randomVec(3, 7),randomDirection()));
                    break;
                case LEFT:
                    scoreRight +=1;
                    this.b = new Ball(new Vec2(0, 0), 40, Vec2.mul(randomVec(3, 7),randomDirection()));
                    break;
                default:
                    //pass
                    break;
            }
            displayScore();
    }

    public Vec2 randomVec(int min, int max){
        int x = ThreadLocalRandom.current().nextInt(min, max + 1);
        int y = ThreadLocalRandom.current().nextInt(min, max + 1);
        return new Vec2(x,y);
    }

    public int randomDirection(){
        int _v = ThreadLocalRandom.current().nextInt(0, 1 + 1);
        return (_v == 0) ? 1 : -1;
    }

    public void displayDelimiter(){
        frameGraphics.drawLine(cX, 0, cX, 2*cY);
    }

    public void displayScore(){
        int size = 50;
        frameGraphics.drawString("" + scoreLeft, cX - size-28, 100);
        frameGraphics.drawString("" + scoreRight, cX + size, 100);
    }

    private void CheckCollisions(){
        b.checkTopCollision();
        b.checkBottomCollision();
        rightPanel.checkRightCollision(b);
        leftPanel.checkLeftCollision(b);
    }

    public void moveLeftPanelUp(){
        leftPanel.setVelocity(new Vec2(0, panelSpeed));
    }
    public void moveLeftPanelDown(){
        leftPanel.setVelocity(new Vec2(0, -panelSpeed));
    }
    public void stopLeftPanel(){
        leftPanel.setVelocity(new Vec2(0,0));
    }

    public void moveRightPanelUp(){
        rightPanel.setVelocity(new Vec2(0, panelSpeed));
    }
    public void moveRightPanelDown(){
        rightPanel.setVelocity(new Vec2(0, -panelSpeed));
    }
    public void stopRightPanel(){
        rightPanel.setVelocity(new Vec2(0,0));
    }

    private final Graphics g;
    private final BufferedImage frame;
    private final Graphics frameGraphics;
    private Ball b;
    private final Panel leftPanel, rightPanel;
    private int scoreLeft = 0, scoreRight = 0;

    private final int panelSpeed = 5;
    private final int panelWidth = 25;

    public static final int cX = Window.width/2;
    public static final int cY = Window.height/2;
}
