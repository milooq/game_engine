package com.company;

import com.company.drawing.Drawable;
import com.company.physics.CircleBody;
import com.company.physics.Engine;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Ball extends CircleBody implements Drawable {

    public Ball(Vec2 position, int diameter, Vec2 velocity) {
        super(diameter);
        this.position =  Engine.CoordNormalToBad(position);
        this.velocity =  Engine.VelocityConvert(velocity);
        try {
            ball_image = ImageIO.read(new File("ball.png"));
        }catch(IOException e) {
            System.out.println("Не найдена картина мяча!");
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(ball_image, position.getX() - radius/2, position.getY() - radius/2, radius, radius, null);
    }

    @Override
    public void update() {
        position.add(velocity);
    }

    public void addVelocity(Vec2 v){
        this.velocity.add(v);
    }

    public int getRadius(){
        return radius;
    }

    public void invXVel(){
        velocity.setX(-velocity.getX());
    }

    public void invYVel(){
        velocity.setY(-velocity.getY());
    }

    public void invVel(){
        invXVel();
        invYVel();
    }

    public void jump(int jmp) { position.setX(position.getX() + jmp);}

    public enum pp {LEFT, RIGHT, NONE};

    public pp playerPoint(){
        if((position.getX() + radius/2 > Window.width)){
            return pp.LEFT;
        }
        if((position.getX() - radius/2 < 0)){
            return  pp.RIGHT;
        }
        return pp.NONE;
    }

    private BufferedImage ball_image;
}
