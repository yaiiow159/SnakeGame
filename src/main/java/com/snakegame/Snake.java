package com.snakegame;

import java.awt.*;
import java.util.ArrayList;

public class Snake {
    private ArrayList<Node> snakeBody;

    public Snake(){
        this.snakeBody = new ArrayList<>();
        snakeBody.add(new Node(80,0));
        snakeBody.add(new Node(60,0));
        snakeBody.add(new Node(40,0));
        snakeBody.add(new Node(20,0));
    }

    public ArrayList<Node> getSnakeBody() {
        return snakeBody;
    }

    //繪製貪吃蛇的位置
    public void drawSnake(Graphics g){
        for (Node node: snakeBody) {
            if(node == snakeBody.get(0)){
                g.setColor(Color.YELLOW);
            } else {
                g.setColor(Color.RED);
            }
            // 判斷貪吃蛇的位置，如果超出遊戲邊界，從另一側出現
            if(node.x >= game.WIDTH){
                node.x = 0;
            }
            if(node.x < 0){
                node.x = game.WIDTH - game.CELL_SIZE;
            }
            if(node.y >= game.HEIGHT){
                node.y = 0;
            }
            if(node.y < 0){
                node.y = game.HEIGHT - game.CELL_SIZE;
            }
            g.fillOval(node.x, node.y, game.CELL_SIZE, game.CELL_SIZE);
        }
    }
}
