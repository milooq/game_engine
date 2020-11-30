package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Panel extends Rectangle {


    public Panel(int width, int height, Vec2 position, Vec2 velocity) {
        super(width, height, Animator.CoordNormalToBad(position),Animator.VelocityConvert(velocity));
        try {
            panel_image = ImageIO.read(new File("panel.png"));
        }catch(IOException e) {
            System.out.println("Пикчу панели не нашел(");
        }
    }

    public void draw(Graphics g){
        g.drawImage(panel_image, position.getX(), position.getY(), width, height, null);
//        g.drawRect(position.getX(), position.getY(), width, height);
    }

    void setVelocity(Vec2 v){
        this.velocity = Animator.VelocityConvert(v);
    }

    public void update(){
        position.add(velocity);
    }

    public void checkRightCollision(Ball b){
        int ypos = b.position.getY();
        int xpos = b.position.getX() + b.getRadius()/2;
        if( ypos >= position.getY() && ypos <= (position.getY() + height) ){
            if(xpos >= position.getX()){
                b.invXVel();
                b.invYVel();
                b.jump(position.getX() - xpos);
                b.addVelocity(this.velocity);
            }
        }
    }

    public void checkLeftCollision(Ball b){
        int ypos = b.position.getY();
        int xpos = b.position.getX() - b.getRadius()/2;

        if( ypos >= position.getY() && ypos <= (position.getY() + height) ){
            if(xpos <= position.getX() + width){
                b.invXVel();
                b.invYVel();
                b.jump((position.getX() + width) - xpos);
                b.addVelocity(this.velocity);
            }
        }
    }

    private BufferedImage panel_image;
}
