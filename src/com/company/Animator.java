package com.company;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Animator extends Thread{

    private Graphics g;
    BufferedImage frame;
    Graphics frameGraphics;
    Ball b;
    Panel p1, p2;

     private int panelSpeed = 2;

    public static int cX = Window.width/2;
    public static int cY = Window.height/2;

    public Animator(Graphics g) {
        this.g = g;
        frame = new BufferedImage(Window.width,Window.height,BufferedImage.TYPE_INT_RGB );
        frameGraphics = frame.getGraphics();
        int x = ThreadLocalRandom.current().nextInt(3, 7 + 1);
        int y = ThreadLocalRandom.current().nextInt(3, 7 + 1);
        b = new Ball(new Vec2(0, 0), 40, new Vec2(x,y));
        p1 = new Panel(20, 100, new Vec2(-cX + 20, 50), new Vec2(0, 0));
        p2 = new Panel(20, 100, new Vec2(cX - 40, 50), new Vec2(0, 0));
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

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clear();
            CheckCollisions();
            b.update();
            p1.update();
            p2.update();
            b.draw(frameGraphics);
            p1.draw(frameGraphics);
            p2.draw(frameGraphics);
            g.drawImage(frame,0,0, Window.width, Window.height,null);
        }
    }

    private void CheckCollisions(){
        b.checkTopCollision();
        b.checkBottomCollision();
        p2.checkRightCollision(b);
        p1.checkLeftCollision(b);
    }

    public void moveLUp(){
        p1.setVelocity(new Vec2(0, panelSpeed));
    }
    public void moveLDown(){
        p1.setVelocity(new Vec2(0, -panelSpeed));
    }
    public void stopL(){
        p1.setVelocity(new Vec2(0,0));
    }

    public void moveRUp(){
        p2.setVelocity(new Vec2(0, panelSpeed));
    }
    public void moveRDown(){
        p2.setVelocity(new Vec2(0, -panelSpeed));
    }
    public void stopR(){
        p2.setVelocity(new Vec2(0,0));
    }
}
