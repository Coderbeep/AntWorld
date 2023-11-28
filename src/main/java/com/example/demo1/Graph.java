package com.example.demo1;

import java.util.ArrayList;
import javafx.scene.layout.Pane;
import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

// Class to represent the graph of the world
public class Graph {
    private int numberOfSpots;
    private Pane pane;
    private ArrayList<Spot> spots = new ArrayList<Spot>();
    private Anthill[] anthills = new Anthill[2];
    private static final int BOX_SIZE = 50;
    private static final int DESIRED_SPOT_COUNT = 50;
    private static final int SPOT_RADIUS = 8;

    private void generateAnthills() {
        // RED (300, 450) --- BLUE (900, 250)
        anthills[0] = new Anthill(300, 450, AnthillType.RED);
        anthills[1] = new Anthill(900, 250, AnthillType.BLUE);
    }
    public Graph(int numberOfSpots, Pane pane) {
        this.numberOfSpots = numberOfSpots;
        this.pane = pane;
        generateAnthills();
//        generateSpots();
        createRandomSpots(pane);
    }

    private void generateSpots() {
        // spots in random positions, but not on top of each other
        for (int i = 0; i < numberOfSpots; i++) {
            int x = (int) (Math.random() * 1200);
            int y = (int) (Math.random() * 700);
            Spot spot = new Spot(x, y);
            spots.add(spot);
        }
    }

    private boolean isInsideAnthill(double x, double y) {
        double distanceToAnthill1 = Math.hypot(x - anthills[0].getX(), y - anthills[0].getY());
        double distanceToAnthill2 = Math.hypot(x - anthills[1].getX(), y - anthills[1].getY());

        return distanceToAnthill1 < anthills[0].radius + SPOT_RADIUS ||
                distanceToAnthill2 < anthills[0].radius + SPOT_RADIUS;
    }

    private void createRandomSpots(Pane pane) {
        Random random = new Random();

        int boxesX = (int) pane.getWidth() / BOX_SIZE;
        int boxesY = (int) pane.getHeight() / BOX_SIZE;

        int totalBoxes = boxesX * boxesY;
        double probability = (double) DESIRED_SPOT_COUNT / totalBoxes;

        for (int i = 0; i < boxesX; i++) {
            for (int j = 0; j < boxesY; j++) {
                if (random.nextDouble() < probability) {
                    // create a spot in random position inside the box
                    double centerX = random.nextDouble() * BOX_SIZE + i * BOX_SIZE;
                    double centerY = random.nextDouble() * BOX_SIZE + j * BOX_SIZE;

                    // Check if the spot is not inside any anthill
                    if (!isInsideAnthill(centerX, centerY)) {
                        Circle circle = new Circle(centerX, centerY, SPOT_RADIUS, Color.BLACK);
                        pane.getChildren().add(circle);
                    }
                }
            }
        }
    }

    public void drawOnPane() {
//        for (Spot spot : spots) {
//            spot.drawOnPane(pane);
//        }

        for (Anthill anthill : anthills) {
            anthill.drawOnPane(pane);
        }
    }

}
