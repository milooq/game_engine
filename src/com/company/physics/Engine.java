package com.company.physics;

import com.company.Ball;
import com.company.Panel;
import com.company.Vec2;
import com.company.Window;

import java.util.concurrent.ThreadLocalRandom;

import static com.company.Window.cX;
import static com.company.Window.cY;

public class Engine {

    public Engine(Panel leftPanel, Panel rightPanel, Ball ball) {
        this.leftPanel = leftPanel;
        this.rightPanel = rightPanel;
        this.ball = ball;
    }

    public void update(){
        CheckCollisions();

        leftPanel.update();
        rightPanel.update();
        ball.update();
    }

    public void setNewBall(){
        this.ball.position.setX(Window.cX);
        this.ball.position.setY(Window.cY);
        this.ball.velocity = Vec2.mul(Vec2.randomVec(3, 7),Vec2.randomDirection());
    }

    public void resetPanels(){
        leftPanel.position = CoordNormalToBad(new Vec2(-cX + 20, 50));
        rightPanel.position = CoordNormalToBad(new Vec2(cX - 40, 50));
    }

    private void CheckCollisions(){
        checkBallEdgeCollision();
        checkRightPanelCollision();
        checkLeftPanelCollision();
    }

    private void checkBallEdgeCollision(){
        if(ball.position.getY() - ball.radius/2 <= 30){
            ball.invYVel();
//            ball.addVelocity(collisionNoise(ball.velocity));
        }
        if(ball.position.getY() + ball.radius/2 >= Window.height){
            ball.invYVel();
//            ball.addVelocity(collisionNoise(ball.velocity));
        }
    }

    private void checkRightPanelCollision(){
        int ypos = ball.position.getY();
        int xpos = ball.position.getX() + ball.getRadius()/2;
        int firstPoint = rightPanel.position.getY() + rightPanel.height/3;
        int secondPoint = rightPanel.position.getY() + 2 * rightPanel.height/3;
        double alpha = Math.PI/6;

        if( ypos >= rightPanel.position.getY() && ypos <= (rightPanel.position.getY() + rightPanel.height) ){
            if(xpos >= rightPanel.position.getX()){
                ball.invXVel();
                ball.jump(rightPanel.position.getX() - xpos);
                if(ypos <= firstPoint){
                    ball.velocity.setY((int)(ball.velocity.getX()*Math.tan(alpha)));
                    return;
                }
                if(ypos >= secondPoint){
                    ball.velocity.setY(-(int)(ball.velocity.getX()*Math.tan(alpha)));
                    return;
                }
                ball.velocity.setY(0);
                ball.addVelocity(rightPanel.velocity);
//                ball.addVelocity(collisionNoise(ball.velocity));
            }
        }
    }

    private void checkLeftPanelCollision(){
        int ypos = ball.position.getY();
        int xpos = ball.position.getX() - ball.getRadius()/2;
        int firstPoint = leftPanel.position.getY() + leftPanel.height/3;
        int secondPoint = leftPanel.position.getY() + 2 * leftPanel.height/3;
        double alpha = Math.PI/6;

        if( ypos >= leftPanel.position.getY() && ypos <= (leftPanel.position.getY() + leftPanel.height) ){
            if(xpos <= leftPanel.position.getX() + leftPanel.width){
                ball.invXVel();
                ball.jump((leftPanel.position.getX() + leftPanel.width) - xpos);
                if(ypos <= firstPoint){
                    ball.velocity.setY(-(int)(ball.velocity.getX()*Math.tan(alpha)));
                    return;
                }
                if(ypos >= secondPoint){
                    ball.velocity.setY((int)(ball.velocity.getX()*Math.tan(alpha)));
                    return;
                }
                ball.velocity.setY(0);
                ball.addVelocity(leftPanel.velocity);
//                ball.addVelocity(collisionNoise(ball.velocity));
            }
        }
    }

    private Vec2 collisionNoise(Vec2 velocity){
        Vec2 noise = Vec2.rot90deg(velocity);
        noise.normalize();
        noise.mul(ThreadLocalRandom.current().nextInt(-1, 1 + 1));
        return noise;
    }

    /*
    Переводит
    (Начало в ценре окна, ось X вправо, ось Y вверх) ->
    (Начало в левом верхнем углу окна, ось X вправо, ось Y вниз)
     */
    static public Vec2 CoordNormalToBad(Vec2 v){
        return new Vec2(v.getX() + cX, -v.getY() + cY);
    }
    //См. выше
    static public Vec2 VelocityConvert(Vec2 v){
        return new Vec2(v.getX(), -v.getY());
    }

    private final Panel leftPanel, rightPanel;
    private final Ball ball;
}
