import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {
    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = (int) (GAME_WIDTH * (0.5555)); // proporção de 5:9
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    static final int BALL_DIAMETER = 20;
    static final int PADDLE_WIDTH = 25;
    static final int PADDLE_HEIGHT = 100;
    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    Score score;
    int state;
    Menu menu;
    MouseInput mouse;

    GamePanel() {
        state = 0; //0 == Menu | 1 == Game
        menu = new Menu(GAME_WIDTH, GAME_HEIGHT);
        mouse = new MouseInput(GAME_WIDTH, GAME_HEIGHT, menu, state);
        this.addMouseListener(mouse);
        this.setFocusable(true);
        this.addKeyListener(new ActionListener());
        this.setPreferredSize(SCREEN_SIZE);

        gameThread = new Thread(this);
        gameThread.start();
    }

    public void newBall() {
        random = new Random();
        ball = new Ball((GAME_WIDTH / 2) - (BALL_DIAMETER / 2), random.nextInt(GAME_HEIGHT - BALL_DIAMETER), BALL_DIAMETER, BALL_DIAMETER);
    }

    public void newPaddles() {
        paddle1 = new Paddle(0, (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH, PADDLE_HEIGHT, 1);
        paddle2 = new Paddle(GAME_WIDTH - PADDLE_WIDTH, (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH, PADDLE_HEIGHT, 2);
    }

    public void paint(Graphics g) {
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g) {
        if(state == 1){ //Game
            paddle1.draw(g);
            paddle2.draw(g);
            ball.draw(g);
            score.draw(g);
        }
        else if(state == 0){ //Menu
            menu.draw(g);
        }
    }

    public void move() {
        paddle1.move();
        paddle2.move();
        ball.move();
    }

    public void checkCollision() {
        //faz a bola quicar quando colide com as bordas da janela
        if(ball.y <= 0){
            ball.setYDirection(-ball.yVelocity);
        }
        if(ball.y >= GAME_HEIGHT - BALL_DIAMETER){
            ball.setYDirection(-ball.yVelocity);
        }
        //faz a bola quicar quando colide com as paddles
        if(ball.intersects(paddle1)){
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++; //aumenta a velocidade para dificultar o jogo
            if(ball.yVelocity > 0){
                ball.yVelocity++; //aumenta a velocidade para dificultar o jogo
            }
            else{
                ball.yVelocity--;
            }
            ball.setXDirection(ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }

        if(ball.intersects(paddle2)){
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++; //aumenta a velocidade para dificultar o jogo
            if(ball.yVelocity > 0){
                ball.yVelocity++; //aumenta a velocidade para dificultar o jogo
            }
            else{
                ball.yVelocity--;
            }
            ball.setXDirection(-ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }

        //limita o movimento das paddles nas bordas da janela
        if(paddle1.y <= 0){
            paddle1.y = 0;
        }
        if(paddle1.y >= (GAME_HEIGHT - PADDLE_HEIGHT)){
            paddle1.y = GAME_HEIGHT - PADDLE_HEIGHT;
        }
        if(paddle2.y <= 0){
            paddle2.y = 0;
        }
        if(paddle2.y >= (GAME_HEIGHT - PADDLE_HEIGHT)){
            paddle2.y = GAME_HEIGHT - PADDLE_HEIGHT;
        }

        //da 1 ponto para o jogador e cria novos paddles e bola
        if(ball.x <= 0){
            score.player2++;
            newPaddles();
            newBall();
            System.out.println("2: " + score.player2);
        }

        if(ball.x >= GAME_WIDTH - BALL_DIAMETER){
            score.player1++;
            newPaddles();
            newBall();
            System.out.println("1: " + score.player1);
        }
    }

    public void run() {
        //game loop
        int newMatch = 0;
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while(true){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if(delta >= 1){
                state = mouse.getState();
                
                if(state == 1){ //Game
                    if(newMatch == 0){
                        newPaddles();
                        newBall();
                        score = new Score(GAME_WIDTH, GAME_HEIGHT);
                        newMatch++;
                    }
                    move();
                    checkCollision();
                    repaint();
                    delta--;
                }
            }
        }
    }

    public class ActionListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            if(state == 1){ //Game
                paddle1.keyPressed(e);
                paddle2.keyPressed(e);
            }
        }

        public void keyReleased(KeyEvent e) {
            if(state == 1){ //Game
                paddle1.keyReleased(e);
                paddle2.keyReleased(e);
            }
        }
    }
}
