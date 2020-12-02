package com.company;

import com.company.drawing.Drawable;
import com.company.physics.CircleBody;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Ball extends CircleBody implements Drawable {

    public Ball(Vec2 position, int diameter, Vec2 velocity) {
        super(diameter);
        this.position =  Animator.CoordNormalToBad(position);
        this.velocity =  Animator.VelocityConvert(velocity);
        try {
            ball_image = ImageIO.read(new File("ball.png"));
        }catch(IOException e) {
            System.out.println("Не найдена картина мяча!");
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(ball_image, position.getX() - radius/2, position.getY() - radius/2, radius, radius, null);
//        g.drawOval(position.getX() - radius/2, position.getY() - radius/2, radius, radius);
    }

    @Override
    public void update() {
        position.add(velocity);
    }

    void addVelocity(Vec2 v){
        this.velocity.add(v);
    }

    void mulVelocity(int alpha){this.velocity.mul(alpha);}

    public int getRadius(){
        return radius;
    }

    public Vec2 getPosition(){
        return position;
    }

    public void invXVel(){
        velocity.setX(-velocity.getX());
    }

    public void invYVel(){
        velocity.setY(-velocity.getY());
    }

    public void jump(int jmp) { position.setX(position.getX() + jmp);}

    public void checkTopCollision() {
        if(position.getY() - radius/2 <= 30){
            invYVel();
        }
    }

    public void checkBottomCollision() {
        if(position.getY() + radius/2 >= Window.height){
            invYVel();
        }
    }

    public enum eOutOfBounce {NONE, LEFT, RIGHT};

    public Ball.eOutOfBounce OutOfBonce(){
        if((position.getX() + radius/2 > Window.width)){
            return Ball.eOutOfBounce.RIGHT;
        }
        if((position.getX() - radius/2 < 0)){
            return  Ball.eOutOfBounce.LEFT;
        }
        return Ball.eOutOfBounce.NONE;
    }

    private BufferedImage ball_image;
}
