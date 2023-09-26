package com.snakegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.SecureRandom;
import java.util.*;
import java.util.Timer;

public class game extends JPanel implements KeyListener {
    public static final int CELL_SIZE = 20;
    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;

    public static final int row = HEIGHT / CELL_SIZE;

    public static final int col = WIDTH / CELL_SIZE;
    private Snake snake;
    private Fruit fruit;
    private Bomb bomb;
    private int speed = 100;
    private static String direction;
    private boolean allowKeyPress = true;
    private Timer timer;
    private int score;
    private static int highestScore;

    public game() {
        reset();
        addKeyListener(this);
    }

    public static void main (String[]args){
        // 製作 貪吃蛇form
        JFrame frame = new JFrame("Snake Game");
        frame.setTitle("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new game());
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setBackground(Color.BLACK);
    }

    @Override
    public Dimension getPreferredSize() {
        // 設定視窗大小
        return new Dimension(WIDTH, HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        //檢查是否咬到自己
        ArrayList<Node> snakeBody = snake.getSnakeBody();
        // 取得蛇頭來跟其他部位做比對如果重疊代表咬到自己
        Node head = snakeBody.get(0);
        for (int i = 1; i < snakeBody.size(); i++) {
            int resp;
            if (snakeBody.get(i).x == head.x && snakeBody.get(i).y == head.y) {
                resp = gameOver();
                switch (resp) {
                    case JOptionPane.CLOSED_OPTION:
                        System.exit(0);
                        break;
                    case JOptionPane.NO_OPTION:
                        System.exit(0);
                        break;
                    case JOptionPane.YES_OPTION:
                        reset();
                        return;
                }
            }
            if(snakeBody.get(i).x == bomb.getX() && snakeBody.get(i).y == bomb.getY()){
                resp = gameOver();
                switch (resp) {
                    case JOptionPane.CLOSED_OPTION:
                        System.exit(0);
                        break;
                    case JOptionPane.NO_OPTION:
                        System.exit(0);
                        break;
                    case JOptionPane.YES_OPTION:
                        reset();
                        return;
                }
            }
        }
        // draw a black background
        g.fillRect(0, 0, WIDTH, HEIGHT);
        fruit.drawFruit(g);
        snake.drawSnake(g);
        bomb.drawBomb(g);
        int snakeX = snake.getSnakeBody().get(0).x;
        int snakeY = snake.getSnakeBody().get(0).y;

        // 往右的方向，x = x + CELL_SIZE
        if (direction.equals("Right")) {
            snakeX += CELL_SIZE;
        }
        // 往左的方向,x = x - CELL_SIZE
        if (direction.equals("Left")) {
            snakeX -= CELL_SIZE;
        }
        // 往上的方向,y = y - CELL_SIZE
        if (direction.equals("Up")) {
            snakeY -= CELL_SIZE;
        }
        // 往下的方向,y = y + CELL_SIZE
        if (direction.equals("Down")) {
            snakeY += CELL_SIZE;
        }
        // 把貪吃蛇的尾巴位置去掉 index首 + 1 達成移動動作
        Node headNode = new Node(snakeX, snakeY);
        // 檢查是否有吃到食物了，有吃到則增加一個cell-size的長度
        if (snake.getSnakeBody().get(0).x == fruit.getX() && snake.getSnakeBody().get(0).y == fruit.getY()) {
            //有吃到食物了
            score++;
            highestScore = Math.max(score, highestScore);
            //設置食物到隨機位置
            fruit.setNewLocation(snake.getSnakeBody());
            // 重新繪製新的水果
            fruit.drawFruit(g);
        } else {
            snake.getSnakeBody().remove(snake.getSnakeBody().size() - 1);
        }
        snake.getSnakeBody().add(0, headNode);
        // 重新定位貪吃蛇的位置
        allowKeyPress = true;
        requestFocusInWindow();

    }
    private void reset () {
        if(highestScore == 0){
            highestScore = score;
        }
        score = 0;
        if (snake != null) {
            snake.getSnakeBody().clear();
        }
        allowKeyPress = true;
        direction = "Right";
        snake = new Snake();
        fruit = new Fruit();
        bomb = new Bomb();
        setDefaultTimer();
    }
    private void setDefaultTimer () {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        }, 0, speed);
    }

    private int gameOver () {
        allowKeyPress = false;
        timer.cancel();
        timer.purge();
        int resp = JOptionPane.showOptionDialog(
                this, "Game Over! The total score is " + score + " and the highest score is " + highestScore + ". Would you play again?",
                "Game Over",
                JOptionPane.NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                null,
                JOptionPane.YES_OPTION);
        return resp;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (allowKeyPress) {
            if (e.getKeyCode() == 37 && !direction.equals("Right")) {
                direction = "Left";
            } else if (e.getKeyCode() == 38 && !direction.equals("Down")) {
                direction = "Up";
            } else if (e.getKeyCode() == 39 && !direction.equals("Left")) {
                direction = "Right";
            } else if (e.getKeyCode() == 40 && !direction.equals("Up")) {
                direction = "Down";
            }
            allowKeyPress = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
