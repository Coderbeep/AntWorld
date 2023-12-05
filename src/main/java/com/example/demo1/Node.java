package com.example.demo1;

import javafx.scene.layout.Pane;

public abstract class Node {
    private int x = 0;
    private int y = 0;
    abstract public void drawOnPane(Pane pane);

    public int getX() { return x; }
    public int getY() { return y; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
}

