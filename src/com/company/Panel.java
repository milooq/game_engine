package com.company;

import com.company.drawing.Drawable;
import com.company.physics.Body;
import com.company.physics.CircleBody;
import com.company.physics.Engine;
import com.company.physics.RectangleBody;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Panel extends RectangleBody implements Drawable {


    public Panel(int width, int height, Vec2 position, Vec2 velocity) {
        super(width,height);
        this.position = Engine.CoordNormalToBad(position);
        this.velocity = Engine.VelocityConvert(velocity);
        try {
            panel_image = ImageIO.read(new File("panel.png"));
        }catch(IOException e) {
            System.out.println("Не найдена картина панельки!");
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(panel_image, position.getX(), position.getY(), width, height, null);
    }

    void setVelocity(Vec2 v){
        this.velocity = Engine.VelocityConvert(v);
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
    private BufferedImage panel_image;
}
