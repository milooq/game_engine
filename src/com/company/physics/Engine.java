package com.company.physics;

import com.company.Ball;
import com.company.Panel;
import com.company.Window;

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

    public void setNewBall(Ball ball){
        this.ball = ball;
    }

    private void CheckCollisions(){
        checkBallEdgeCollision();
        checkRightPanelCollision();
        checkLeftPanelCollision();
    }

    private void checkBallEdgeCollision(){
        if(ball.position.getY() - ball.radius/2 <= 30){
            ball.invYVel();
        }
        if(ball.position.getY() + ball.radius/2 >= Window.height){
            ball.invYVel();
        }
    }

    private void checkRightPanelCollision(){
        int ypos = ball.position.getY();
        int xpos = ball.position.getX() + ball.getRadius()/2;

        if( ypos >= rightPanel.position.getY() && ypos <= (rightPanel.position.getY() + rightPanel.height) ){
            if(xpos >= rightPanel.position.getX()){
                ball.invVel();
                ball.jump(rightPanel.position.getX() - xpos);
                ball.addVelocity(rightPanel.velocity);
            }
        }
    }

    private void checkLeftPanelCollision(){
        int ypos = ball.position.getY();
        int xpos = ball.position.getX() - ball.getRadius()/2;

        if( ypos >= leftPanel.position.getY() && ypos <= (leftPanel.position.getY() + leftPanel.height) ){
            if(xpos <= leftPanel.position.getX() + leftPanel.width){
                ball.invVel();
                ball.jump((leftPanel.position.getX() + leftPanel.width) - xpos);
                ball.addVelocity(leftPanel.velocity);
            }
        }
    }

    private final Panel leftPanel, rightPanel;
    private Ball ball;
}
