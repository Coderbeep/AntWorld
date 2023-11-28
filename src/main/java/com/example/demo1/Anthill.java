package com.example.demo1;

import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
enum AnthillType {
    RED(Color.RED), BLUE(Color.BLUE);
    private final Color color;

    AnthillType(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}

public class Anthill extends Node {
    public AnthillType type;
    public int radius = 25;

    public Anthill(int x, int y, AnthillType type) {
        this.setX(x);
        this.setY(y);
        this.type = type;
    }

    @Override
    public void drawOnPane(Pane pane) {
        Color fillColor = type.getColor();
        Circle circle = new Circle(this.getX(), this.getY(), radius, fillColor);
        pane.getChildren().add(circle);
    }
}
