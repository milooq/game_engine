package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Ball extends Circle {
    public Ball(Vec2 position, int diameter, Vec2 velocity) {
        super(Animator.CoordNormalToBad(position), diameter, Animator.VelocityConvert(velocity));
        try {
            ball_image = ImageIO.read(new File("ball.png"));
        }catch(IOException e) {
            System.out.println("Пикчу шара не нашел(");
        }
    }

    public void draw(Graphics g){
        g.drawImage(ball_image, position.getX() - radius/2, position.getY() - radius/2, radius, radius, null);
//        g.drawOval(position.getX() - radius/2, position.getY() - radius/2, radius, radius);
    }

    void addVelocity(Vec2 v){
        this.velocity.add(v);
    }

    void mulVelocity(int alpha){this.velocity.mul(alpha);}

    public void update(){
        position.add(velocity);
    }

    public int getRadius(){
        return radius ;
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

    public eOutOfBounce OutOfBonce(){
        if((position.getX() + radius/2 > Window.width)){
            return eOutOfBounce.RIGHT;
        }
        if((position.getX() - radius/2 < 0)){
            return  eOutOfBounce.LEFT;
        }
        return eOutOfBounce.NONE;
    }

    private BufferedImage ball_image;
}
