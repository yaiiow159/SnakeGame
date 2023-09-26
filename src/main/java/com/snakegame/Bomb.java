package com.snakegame;

import java.awt.*;
import java.util.ArrayList;

public class Bomb {
    private int x;
    private int y;

    public Bomb(){
        this.x = (int)(Math.floor(Math.random() * game.col) * game.CELL_SIZE);
        this.y = (int)(Math.floor(Math.random() * game.row) * game.CELL_SIZE);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void drawBomb(Graphics g){
        g.setColor(Color.MAGENTA);
        g.fillOval(this.x, this.y, game.CELL_SIZE, game.CELL_SIZE);
    }

    public void reGenerateBomb(Fruit fruit, Snake snake){
        // 要避開水果生成位置
        int newX;
        int newY;
        boolean overlapping = Boolean.FALSE;
        do {
            newX = (int)(Math.floor(Math.random() * game.col) * game.CELL_SIZE);
            newY = (int)(Math.floor(Math.random() * game.row) * game.CELL_SIZE);
            overlapping = validateCollision(snake.getSnakeBody(),fruit, newX, newY);
        } while (overlapping);
    }

    private boolean validateCollision(ArrayList<Node> snakeBody,Fruit fruit ,int newX, int newY) {
        for (Node node : snakeBody) {
            if (node.x == newX && node.y == newY) {
                return Boolean.TRUE;
            }
            if(node.x == fruit.getX() && node.y == fruit.getY()){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
