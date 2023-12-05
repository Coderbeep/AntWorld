package com.example.demo1;

import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public abstract class Ant implements Runnable {
    private int ANT_RECT_WIDTH = 10;
    private int ANT_RECT_HEIGHT = 10;

    private int x;
    private int y;
    private String antName;
    private Color antColor;
    private int antStrength;
    private int antHealth;
    private Anthill antHome;
    private Rectangle antRect;

    private Spot currentNode;

    public Ant(Anthill antHome, Color color, String name, int strength, int health) {
        this.antHome = antHome;
        this.antColor = color;
        this.antName = name;
        this.antStrength = strength;
        this.antHealth = health;

        currentNode = antHome;
        this.x = antHome.getX();
        this.y = antHome.getY();
        System.out.println(" --- Ant " + antName + " is at spot " + currentNode.id);
        System.out.println(" --- Ant coordinates: (" + x + ", " + y + ")");

        spawn();
    }

    private void spawn() {
        Rectangle rect = new Rectangle();
        rect.setWidth(ANT_RECT_WIDTH);
        rect.setHeight(ANT_RECT_HEIGHT);
        rect.setFill(Color.TURQUOISE);

        rect.setX(x - Math.round((float) ANT_RECT_WIDTH / 2));
        rect.setY(y - Math.round((float) ANT_RECT_HEIGHT / 2));

        this.antRect = rect;
        PaneManager.getInstance().getPane().getChildren().add(rect);
    }

    @Override
    public void run() {
        // Example animation loop using TranslateTransition
        TranslateTransition transition = new TranslateTransition(Duration.seconds(2), antRect);

        // Get a random neighbor of the current node
        ArrayList<Spot> neighbors = currentNode.neighbors;
        Spot targetSpot = neighbors.get((int) (Math.random() * neighbors.size()));

        // Set the target coordinates of the transition
        transition.setByX(targetSpot.getX() - currentNode.getX());
        transition.setByY(targetSpot.getY() - currentNode.getY());

        transition.setOnFinished(event -> {
            System.out.println("Finished!");
            currentNode = targetSpot;
        });
        Platform.runLater(transition::play);
    }





//    public synchronized void move(Spot targetSpot) {
//        TranslateTransition transition = new TranslateTransition(Duration.seconds(2), antRect);
//        transition.setByX(targetSpot.getX() - currentNode.getX());
//        transition.setByY(targetSpot.getY() - currentNode.getY());
//
//        transition.setOnFinished(event -> {
//            currentNode = targetSpot;
//            x = targetSpot.getX();  // Update x coordinate
//            y = targetSpot.getY();  // Update y coordinate
//
//            synchronized (this) {
//                this.notify();  // Notify the waiting thread that the move is complete
//                System.out.println("Got to spot " + currentNode.id);
//            }
//        });
//
//        Platform.runLater(transition::play);
//    }
//
//    public void spawn() {
//        Rectangle rect = new Rectangle();
//        rect.setWidth(ANT_RECT_WIDTH);
//        rect.setHeight(ANT_RECT_HEIGHT);
//        rect.setFill(Color.TURQUOISE);
//
//        // Get initial coordinates from antHome (Anthill)
//        this.x = antHome.getX();
//        this.y = antHome.getY();
//
//        rect.setX(x - Math.round((float) ANT_RECT_WIDTH / 2));
//        rect.setY(y - Math.round((float) ANT_RECT_HEIGHT / 2));
//
//        this.antRect = rect;
//        PaneManager.getInstance().getPane().getChildren().add(rect);
//    }
//
//    @Override
//    public void run() {
//        Platform.runLater(this::spawn);
//        System.out.println("Ant " + antName + " has spawned!");
//
//        new AnimationTimer() {
//            private long lastUpdate = 0;
//
//            @Override
//            public void handle(long now) {
//                if (now - lastUpdate >= TimeUnit.SECONDS.toNanos(3)) {
//                    ArrayList<Spot> neighbors = currentNode.neighbors;
//                    Spot targetSpot = neighbors.get((int) (Math.random() * neighbors.size()));
//
//                    Platform.runLater(() -> {
//                        move(targetSpot);
//                    });
//
//                    lastUpdate = now;
//                }
//            }
//        }.start();
//    }

    public int getX() { return x; }
    public int getY() { return y; }
    public Color getColor() { return antColor; }
    public Anthill getHome() { return antHome; }
    public String getName() { return antName; }
    public int getStrength() { return antStrength; }
    public Spot getCurrentSpot() { return currentNode; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setColor(Color color) { this.antColor = color; }
    public void setHome(Anthill anthill) {this.antHome = anthill; }
    public void setName(String name) { this.antName = name; }
    public void setStrength(int strength) { this.antStrength = strength; }
    public void setCurrentSpot(Spot spot) { this.currentNode = spot; }

    public String toString() {
        return "Name: " + antName + " --- Strength: " + antStrength + " --- Health: " + antHealth;
    }
}