package com.company.drawing;

import com.company.Ball;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static com.company.Window.cX;
import static com.company.Window.cY;

public class Animator{

    public Animator(Graphics g) {
        //Настройки графики
        this.g = g;
        frame = new BufferedImage(com.company.Window.width, com.company.Window.height,BufferedImage.TYPE_INT_RGB);
        frameGraphics = frame.getGraphics();
        frameGraphics.setFont(new Font("TimesRoman", Font.PLAIN, 50));
        this.drawables = new ArrayList<>();
    }

    private void clear(){
        frameGraphics.setColor(Color.white);
        frameGraphics.fillRect(0,0, com.company.Window.width, com.company.Window.height);
        frameGraphics.setColor(Color.black);
    }

    public void draw() {
        //RenderGame
        clear();
        displayScore();
        displayDelimiter();
        for(Drawable d : drawables){
            d.draw(frameGraphics);
        }
        g.drawImage(frame,0,0, com.company.Window.width, com.company.Window.height,null);
    }

    private void displayDelimiter(){
        frameGraphics.drawLine(cX, 0, cX, 2*cY);
    }

    private void displayScore(){
        int size = 50;
        frameGraphics.drawString("" + com.company.Window.score[Ball.pp.LEFT.ordinal()], cX - size-28, 100);
        frameGraphics.drawString("" + com.company.Window.score[Ball.pp.RIGHT.ordinal()], cX + size, 100);
    }

    public void addDrawable(Drawable d){
        drawables.add(d);
    }

    private final Graphics g;
    private final BufferedImage frame;
    private final Graphics frameGraphics;

    ArrayList<Drawable> drawables;
}
