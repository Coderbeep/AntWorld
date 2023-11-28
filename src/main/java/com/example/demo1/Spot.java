package com.example.demo1;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

// Class to represent a spot on the graph where ants can go to
public class Spot extends Node {
    public Spot(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

    @Override
    public void drawOnPane(Pane pane) {
        Circle circle = new Circle(this.getX(), this.getY(), 8, Color.BLACK);
        pane.getChildren().add(circle);
    }
}
