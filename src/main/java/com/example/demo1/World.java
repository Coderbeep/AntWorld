package com.example.demo1;

import java.util.ArrayList;

import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Random;

import javafx.util.Pair;

// Class to represent the graph of the world
public class World {
    private static final int BOX_SIZE = 25;
    private static final int DESIRED_SPOT_COUNT = 40;
    private static final int SPOT_RADIUS = Spot.SPOT_RADIUS;

    private final Pane pane = PaneManager.getInstance().getPane();
    private final ArrayList<Spot> spots = new ArrayList<>();
    private final ArrayList<Path> paths = new ArrayList<>();
    private final Anthill[] anthills = new Anthill[2];
    private AntFactory antFactory;

    private void generateAnthills() {
        // RED (300, 450) --- BLUE (900, 250)
        anthills[0] = new Anthill(300, 450, Anthill.Type.RED);
        anthills[1] = new Anthill(900, 250, Anthill.Type.BLUE);
        antFactory = new AntFactory(anthills[0], anthills[1]);
    }

    public World() {
        generateAnthills();
        haltonSequence(5,7);
    }

    private void haltonSequence(int base1, int base2) {
        for (int i = 0; i < DESIRED_SPOT_COUNT; i++) {
            double x = halton(i, base1);
            double y = halton(i, base2);
            x = x * this.pane.getWidth();
            y = y * this.pane.getHeight();
            if (!isInsideAnthill(x, y)) {
                spots.add(new Spot((int) x, (int) y));
            }
        }
    }

    private double halton(int i, int base1) {
        double f = 1;
        double r = 0;
        while (i > 0) {
            f = f / base1;
            r = r + f * (i % base1);
            i = i / base1;
        }
        return r;
    }

    private boolean isInsideAnthill(double x, double y) {
        double distanceToAnthill1 = Math.hypot(x - anthills[0].getX(), y - anthills[0].getY());
        double distanceToAnthill2 = Math.hypot(x - anthills[1].getX(), y - anthills[1].getY());

        return distanceToAnthill1 < anthills[0].radius + SPOT_RADIUS ||
                distanceToAnthill2 < anthills[0].radius + SPOT_RADIUS;
    }

    private void createRandomSpots() {
        Random random = new Random();

        int boxesX = (int) this.pane.getWidth() / BOX_SIZE;
        int boxesY = (int) this.pane.getHeight() / BOX_SIZE;

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
                        spots.add(new Spot((int) centerX, (int) centerY));
                    }
                }
            }
        }
    }

    private void addRandomConnections() {
//        Random random = new Random();
//        int numberOfConnections = (int) (spots.size() / 4);
//
//        while (numberOfConnections > 0) {
//            Spot spot1 = spots.get(random.nextInt(spots.size()));
//            Spot spot2 = spots.get(random.nextInt(spots.size()));
//
//            if (spot1 != spot2 && !spot1.neighbors.contains(spot2)) {
//                spot1.degree++;
//                spot2.degree++;
//                spot1.neighbors.add(spot2);
//                spot2.neighbors.add(spot1);
//
//                Line path = new Line(spot1.getX(), spot1.getY(), spot2.getX(), spot2.getY());
//                path.setStroke(Color.BLACK);
//                paths.add(path);
//                numberOfConnections--;
//            }
//        }

        ArrayList<Pair<Spot, Spot>> pairs = new ArrayList<>();
        pairs.add(new Pair<>(spots.get(22), spots.get(7)));
        pairs.add(new Pair<>(spots.get(30), spots.get(1)));
        pairs.add(new Pair<>(spots.get(8), spots.get(3)));
        pairs.add(new Pair<>(spots.get(9), spots.get(38)));
        pairs.add(new Pair<>(spots.get(21), spots.get(2)));
        pairs.add(new Pair<>(spots.get(12), spots.get(13)));
        pairs.add(new Pair<>(spots.get(10), spots.get(25)));

        // remove edge between 2 and 21


        for (Pair<Spot, Spot> pair : pairs) {
            Spot spot1 = pair.getKey();
            Spot spot2 = pair.getValue();
            spot1.degree++;
            spot2.degree++;
            spot1.neighbors.add(spot2);
            spot2.neighbors.add(spot1);

            Path path = new Path(spot1, spot2);
            paths.add(path);
        }

        // remove path between 11 and 25
        Path pathToRemove = null;
        for (Path path : paths) {
            if (path.start == spots.get(25) && path.end == spots.get(11)) {
                pathToRemove = path;
            }
        }
        paths.remove(pathToRemove);
    }

    private void connectAnthills() {
        // connect anthills to the graph with 3 closest spots
        for (Anthill anthill: anthills) {
            //create a fake spot for the anthill
//            Spot anthillSpot = new Spot(anthill.getX(), anthill.getY());
//            spots.add(anthillSpot);


            // find the 3 closest spots to the anthill
            for (int i = 0; i < 3; i++) {
                Spot closestSpot = getClosestSpot(anthill);
                if (closestSpot != null) {
                    anthill.degree++;
                    closestSpot.degree++;
                    anthill.neighbors.add(closestSpot);
                    closestSpot.neighbors.add(anthill);
                    Path path = new Path(anthill, closestSpot);
                    paths.add(path);
                }
            }
        }
    }

    private void connectSpots() {
        // Connect each spot of the graph to the nearest spot, ensuring no cycles are formed
        // Degrees of spots are supposed to be 1 or 2
        for (Spot spot : spots) {
            if (spot.degree == 2) {
                continue;
            }
            Spot closestSpot = getClosestSpotWithoutCycle(spot);
            if (closestSpot != null) {
                spot.degree++;
                closestSpot.degree++;
                spot.neighbors.add(closestSpot);
                closestSpot.neighbors.add(spot);
                Path path = new Path(spot, closestSpot);
                paths.add(path);
                // Union the connected spots to avoid cycles
                union(spot, closestSpot);
            }
        }
    }

    private Spot getClosestSpot(Spot spot) {
        // Return the closest spot to the given spot
        double minDistance = Double.MAX_VALUE;
        Spot closestSpot = null;
        for (Spot otherSpot : spots) {
            if (otherSpot != spot && !otherSpot.neighbors.contains(spot)) {
                double distance = distanceBetweenSpots(spot, otherSpot);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestSpot = otherSpot;
                }
            }
        }
        return closestSpot;
    }

    private Spot getClosestSpotWithoutCycle(Spot spot) {
        // Return the closest spot to the given spot without forming a cycle
        double minDistance = Double.MAX_VALUE;
        Spot closestSpot = null;
        for (Spot otherSpot : spots) {
            if (otherSpot != spot && otherSpot.degree < 2 && !areConnected(spot, otherSpot)) {
                double distance = distanceBetweenSpots(spot, otherSpot);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestSpot = otherSpot;
                }
            }
        }
        return closestSpot;
    }

    private boolean areConnected(Spot spot1, Spot spot2) {
        // Check if spot1 and spot2 are connected in the same subgraph
        return findRoot(spot1) == findRoot(spot2);
    }

    private Spot findRoot(Spot spot) {
        // Find the root of the component containing the given spot using Union-Find
        while (spot.parent != null) {
            spot = spot.parent;
        }
        return spot;
    }

    private void union(Spot spot1, Spot spot2) {
        // Union two components by setting one as the parent of the other
        Spot root1 = findRoot(spot1);
        Spot root2 = findRoot(spot2);

        if (root1 != root2) {
            root2.parent = root1;
        }
    }

    private double distanceBetweenSpots(Spot spot1, Spot spot2) {
        // Return the distance between two spots
        return Math.hypot(spot1.getX() - spot2.getX(), spot1.getY() - spot2.getY());
    }

    private void connectSubgraphs() {
        HashMap<Spot, ArrayList<Spot>> spotToSubgraph = new HashMap<>();
        for (Spot spot : spots) {
            Spot root = findRoot(spot);
            if (!spotToSubgraph.containsKey(root)) {
                spotToSubgraph.put(root, new ArrayList<>());
            }
            spotToSubgraph.get(root).add(spot);
        }

        if (spotToSubgraph.size() <= 1) {
            return;
        }



        int neededConnections = spotToSubgraph.size() - 1;
        ArrayList<Spot> usedSpots = new ArrayList<>();
        HashMap<Spot, ArrayList<Spot>> rootsToOtherRoots = new HashMap<>();

        for (Spot root : spotToSubgraph.keySet()) {
            rootsToOtherRoots.put(root, new ArrayList<>());
        }

        for (int i = 0; i < neededConnections; i++) {
            Spot spot1 = null;
            Spot spot2 = null;
            double minDistance = Double.MAX_VALUE;
            for (Spot root1 : spotToSubgraph.keySet()) {
                for (Spot root2 : spotToSubgraph.keySet()) {
                    if (root1 != root2 && !rootsToOtherRoots.get(root1).contains(root2)) {
                        for (Spot spotFromRoot1 : spotToSubgraph.get(root1)) {
                            for (Spot spotFromRoot2 : spotToSubgraph.get(root2)) {
                                double distance = distanceBetweenSpots(spotFromRoot1, spotFromRoot2);
                                if (distance < minDistance && !usedSpots.contains(spotFromRoot1) && !usedSpots.contains(spotFromRoot2)) {
                                    minDistance = distance;
                                    spot1 = spotFromRoot1;
                                    spot2 = spotFromRoot2;
                                }
                            }
                        }
                    }
                }
            }

            spot1.degree++;
            spot2.degree++;
            spot1.neighbors.add(spot2);
            spot2.neighbors.add(spot1);
            Path path = new Path(spot1, spot2);
            paths.add(path);

            usedSpots.add(spot1);
            usedSpots.add(spot2);

            rootsToOtherRoots.get(findRoot(spot1)).add(findRoot(spot2));
            rootsToOtherRoots.get(findRoot(spot2)).add(findRoot(spot1));


            // Union the connected spots to avoid cycles
            union(spot1, spot2);
        }
    }

    private void connectSubgraphsByDegreeZero() {
        int firstDegreeSpotsCount = 0;
        ArrayList<Spot> firstDegreeSpots = new ArrayList<>();
        for (Spot spot : spots) {
            if (spot.degree == 1) {
                firstDegreeSpotsCount++;
                firstDegreeSpots.add(spot);
            }
        }
        if (firstDegreeSpotsCount == 0) {
            return;
        }

        int numberOfSubgraphs = firstDegreeSpotsCount / 2;
        for (Spot spot : firstDegreeSpots) {
            // Find closest spots between different subgraphs
            Spot closestSpot = getClosestSpotFromDifferentSubgraph(spot);
            if (closestSpot != null) {
                spot.degree++;
                closestSpot.degree++;
                spot.neighbors.add(closestSpot);
                closestSpot.neighbors.add(spot);
                Path path = new Path(spot, closestSpot);
                paths.add(path);
                // Union the connected spots to avoid cycles
                union(spot, closestSpot);
            }
        }
    }

    private Spot getClosestSpotFromDifferentSubgraph(Spot spot) {
        // Return the closest spot to the given spot from a different subgraph
        double minDistance = Double.MAX_VALUE;
        Spot closestSpot = null;
        for (Spot otherSpot : spots) {
            if (otherSpot != spot && !areConnected(spot, otherSpot)) {
                double distance = distanceBetweenSpots(spot, otherSpot);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestSpot = otherSpot;
                }
            }
        }
        if (closestSpot != null) {
            System.out.println("Closest spot to " + spot.id + " is " + closestSpot.id);
        }
        else {
            System.out.println("Closest spot: null");
        }
        return closestSpot;
    }

    public void drawOnPane() {
        for (Spot spot : spots) {
            spot.drawOnPane(this.pane);
        }

        for (Anthill anthill : anthills) {
            anthill.drawOnPane(this.pane);
        }

        connectSpots();
        connectSubgraphs();
        addRandomConnections();
        connectAnthills();

        for (Path path : paths) {
            path.drawOnPane(this.pane);
        }
    }

    public Anthill[] getAnthills() { return anthills; }
    public ArrayList<Spot> getSpots() { return spots; }
    public ArrayList<Path> getPaths() { return paths; }
}
