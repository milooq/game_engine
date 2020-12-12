package com.company.drawing;

import com.company.Ball;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static com.company.Window.*;

public class Animator{

    public Animator(Graphics g) {
        //Настройки графики
        this.g = g;
        frame = new BufferedImage(com.company.Window.width, com.company.Window.height,BufferedImage.TYPE_INT_RGB);
        frameGraphics = (Graphics2D) frame.getGraphics();
        frameGraphics.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_GASP); //сглаживание для текста
        gameFont = new Font("TimesRoman", Font.PLAIN, 50);
        countDownFont = new Font("TimesRoman", Font.BOLD, 150);
        frameGraphics.setFont(gameFont);
        this.drawables = new ArrayList<>();
    }

    private void clear(){
        if(backgroundImage == null){
            frameGraphics.setColor(Color.white);
            frameGraphics.fillRect(0,0, com.company.Window.width, com.company.Window.height);
            frameGraphics.setColor(Color.black);
        }
        frameGraphics.drawImage(backgroundImage, 0,0, com.company.Window.width, com.company.Window.height, null);
        frameGraphics.setColor(Color.black);
    }

    public void draw() {
        render();
        swapBuffer();
    }

    private void render(){
        //RenderGame
        clear();
        displayScore();
        displayDelimiter();
        for(Drawable d : drawables){
            d.draw(frameGraphics);
        }
    }

    private void swapBuffer(){
        g.drawImage(frame,0,0, com.company.Window.width, com.company.Window.height,null);
    }

    public void countDown() throws InterruptedException {
        for(int i = 3; i >= 1; --i){
            render();
            frameGraphics.setFont(countDownFont); //для обратного отсчета использует большие цифры
            int width = frameGraphics.getFontMetrics().stringWidth("" + i);
            frameGraphics.drawString("" + i, cX - width/2, cY - 50);
            frameGraphics.setFont(gameFont);
            swapBuffer();
            Thread.sleep(1000);
        }
    }

    private void displayDelimiter(){
        frameGraphics.drawLine(cX, 0, cX, 2*cY);
    }

    private void displayScore(){
        int leftScoreWidth = frameGraphics.getFontMetrics().stringWidth("" + score[Ball.pp.LEFT.ordinal()]);
        int rightScoreWidth = frameGraphics.getFontMetrics().stringWidth("" + score[Ball.pp.RIGHT.ordinal()]);
        frameGraphics.drawString("" + score[Ball.pp.LEFT.ordinal()], cX - leftScoreWidth/2 - 50, 100);
        frameGraphics.drawString("" + score[Ball.pp.RIGHT.ordinal()], cX - rightScoreWidth/2 + 50, 100);
    }

    public void setBackground(BufferedImage image){
        backgroundImage = image;
    }

    public void addDrawable(Drawable d){
        drawables.add(d);
    }

    private final Graphics g;
    private final BufferedImage frame;
    private final Graphics2D frameGraphics;
    private final Font countDownFont, gameFont;

    private BufferedImage backgroundImage;

    ArrayList<Drawable> drawables;
}
