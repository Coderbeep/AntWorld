package com.example.demo1;

import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class Anthill extends Spot {
    public enum Type {
        RED(Color.RED), BLUE(Color.BLUE);
        private final Color color;

        Type(Color color) {
            this.color = color;
        }

        public Color getColor() {
            return color;
        }
    }

    public Type type;
    public int radius = 25;

    public Anthill(int x, int y, Type type) {
        super(x, y);
        this.type = type;
    }

    @Override
    public void drawOnPane(Pane pane) {
        Color fillColor = type.getColor();
        Circle circle = new Circle(this.getX(), this.getY(), radius, fillColor);
        pane.getChildren().add(circle);
    }
}
