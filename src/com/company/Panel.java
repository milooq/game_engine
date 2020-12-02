package com.company;

import com.company.drawing.Drawable;
import com.company.physics.RectangleBody;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Panel extends RectangleBody implements Drawable {


    public Panel(int width, int height, Vec2 position, Vec2 velocity) {
        super(width,height);
        this.position = Animator.CoordNormalToBad(position);
        this.velocity = Animator.VelocityConvert(velocity);
        try {
            panel_image = ImageIO.read(new File("panel.png"));
        }catch(IOException e) {
            System.out.println("Пикчу панели не нашел(");
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(panel_image, position.getX(), position.getY(), width, height, null);
//        g.drawRect(position.getX(), position.getY(), width, height);
    }

    void setVelocity(Vec2 v){
        this.velocity = Animator.VelocityConvert(v);
    }

    @Override
    public void update() {
        boolean move_down = (velocity.getY() > 0);

        if( position.getY() > (40 + 30) && !move_down){
            position.add(velocity);
        }

        if(position.getY() < (Window.height - 40 - 15 - height) && move_down){
            position.add(velocity);
        }
    }

    public void checkRightCollision(Ball b){
        int ypos = b.getPosition().getY();
        int xpos = b.getPosition().getX() + b.getRadius()/2;
        if( ypos >= position.getY() && ypos <= (position.getY() + height) ){
            if(xpos >= position.getX()){
                b.invXVel();
                b.invYVel();
                b.jump(position.getX() - xpos);
                b.addVelocity(this.velocity);
                //эксперимент
                b.addVelocity(new Vec2(1, 0));
            }
        }
    }

    public void checkLeftCollision(Ball b){
        int ypos = b.getPosition().getY();
        int xpos = b.getPosition().getX() - b.getRadius()/2;

        if( ypos >= position.getY() && ypos <= (position.getY() + height) ){
            if(xpos <= position.getX() + width){
                b.invXVel();
                b.invYVel();
                b.jump((position.getX() + width) - xpos);
                b.addVelocity(this.velocity);
                //эксперимент
                b.addVelocity(new Vec2(1, 0));
            }
        }
    }

    private BufferedImage panel_image;
}
