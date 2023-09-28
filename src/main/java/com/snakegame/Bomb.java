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
    public void setX(int i) {
        this.x = i;
    }
    public void setY(int i) {
        this.y = i;
    }
    public void drawBomb(Graphics g){
        g.setColor(Color.MAGENTA);
        g.fillOval(this.x, this.y, game.CELL_SIZE, game.CELL_SIZE);
    }

    public static Bomb generateBomb(Fruit fruit, Snake snake,ArrayList<Bomb> bombList, int number){
        // 要避開水果生成位置以及貪吃蛇位置以及本來炸彈位置
        Bomb newBomb = new Bomb();
        int newX;
        int newY;
        boolean overlapping = Boolean.FALSE;
        do {
            newX = (int)(Math.floor(Math.random() * game.col) * game.CELL_SIZE);
            newY = (int)(Math.floor(Math.random() * game.row) * game.CELL_SIZE);
            overlapping = validateCollision(snake.getSnakeBody(), fruit, bombList, newX, newY);
            number--;
            if(number == 0){
                break;
            }
        } while (overlapping);
        newBomb.setX(newX);
        newBomb.setY(newY);
        return newBomb;
    }

    private static boolean validateCollision(ArrayList<Node> snakeBody, Fruit fruit, ArrayList<Bomb> bombList, int newX, int newY) {
        for (Node node : snakeBody) {
            if (node.x == newX && node.y == newY) {
                return Boolean.TRUE;
            }
        }
        // 判斷炸彈位置是否重疊
        for (Bomb bomb : bombList) {
            if(bomb.getX() == newX && bomb.getY() == newY){
                return Boolean.TRUE;
            }
        }
        // 判斷水果位置是否重疊到新炸彈位置
        if(fruit.getX() == newX && fruit.getY() == newY){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
