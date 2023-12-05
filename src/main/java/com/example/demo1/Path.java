package com.example.demo1;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Path {
    public Node start;
    public Node end;

    public Path(Node start, Node end) {
        this.start = start;
        this.end = end;
    }

    public void drawOnPane(Pane pane) {
        Line line = new Line(start.getX(), start.getY(), end.getX(), end.getY());
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(2);
        pane.getChildren().add(line);
    }
}
