package com.example.demo1;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.ArrayList;


// Class to represent a spot on the graph where ants can go to
public class Spot extends Node {
    public static int idCounter = 0;
    public static final int SPOT_RADIUS = 8;
    public int degree = 0;
    public ArrayList<Spot> neighbors = new ArrayList<>();
    public Spot parent = null;
    public int id;

    public Spot(int x, int y) {
        this.setX(x);
        this.setY(y);
        this.id = idCounter; // Assign the current value of idCounter to id
        idCounter++;
    }

    @Override
    public void drawOnPane(Pane pane) {
        Circle circle = new Circle(this.getX(), this.getY(), 8, Color.BLACK);
        // add text to the circle with the id of the spot
        Text text = new Text(this.getX(), this.getY(), Integer.toString(id));
        text.setFont(javafx.scene.text.Font.font("Verdana", 23));
        text.setFill(Color.LAWNGREEN);
        pane.getChildren().add(circle);
        pane.getChildren().add(text);
    }
}

