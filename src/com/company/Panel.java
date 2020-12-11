package com.company;

import com.company.drawing.Drawable;
import com.company.physics.Engine;
import com.company.physics.RectangleBody;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Panel extends RectangleBody implements Drawable {


    public Panel(int width, int height, Vec2 position, Vec2 velocity) {
        super(width,height);
        this.position = Engine.CoordNormalToBad(position);
        this.velocity = Engine.VelocityConvert(velocity);
    }

    public void setTheme(BufferedImage image){
        panelImage = image;
    }

    @Override
    public void draw(Graphics g) {
        if(panelImage == null){
            g.drawRect(position.getX(), position.getY(), width, height);
            return;
        }
        g.drawImage(panelImage, position.getX(), position.getY(), width, height, null);
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
    private BufferedImage panelImage;
}
