package com.snakegame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Fruit {
    private int x;
    private int y;
    private ImageIcon img;

    public Fruit() {
        // 設定移動位置
 //       img = new ImageIcon("./images/OIP.png","fruit");
        this.x = (int)(Math.floor(Math.random() * game.col) * game.CELL_SIZE);
        this.y = (int)(Math.floor(Math.random() * game.row) * game.CELL_SIZE);
    }

    public void drawFruit(Graphics g){
//        img.paintIcon(null, g, x, y);
        g.setColor(Color.GREEN);
        g.fillOval(this.x, this.y, game.CELL_SIZE, game.CELL_SIZE);
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

    public void setNewLocation(ArrayList<Node> snakeBody){
        int newX = 0;
        int newY = 0;
        boolean overlapping = false;
        // 檢查是否重生在蛇的位置
        do {
            newX = (int)(Math.floor(Math.random() * game.col) * game.CELL_SIZE);
            newY = (int)(Math.floor(Math.random() * game.row) * game.CELL_SIZE);
            overlapping = validateCollision(snakeBody, newX, newY);
        } while (overlapping);

        this.x = newX;
        this.y = newY;
    }

    private boolean validateCollision(ArrayList<Node> snakeBody, int newX, int newY) {
        for (Node node : snakeBody) {
            if (node.x == newX && node.y == newY) {
                return true;
            }
        }
        return false;
    }
}
