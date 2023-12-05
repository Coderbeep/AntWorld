package com.example.demo1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BackgroundFill;

import java.util.ArrayList;

public class AntsWorldGraphApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Ants World app!");

        PaneManager paneManager = PaneManager.getInstance(); // Create a singleton for page Manager

        Pane pane = new Pane();
        Scene scene = new Scene(pane, 1200, 700);
        Background background = new Background(new BackgroundFill(Color.CORNSILK, null, null));
        pane.setBackground(background);

        paneManager.setPane(pane); // Set the pane in PaneManager
        // Create a graph
        World graph = new World();
        graph.drawOnPane();

        // Add ant
        Anthill[] anthills = graph.getAnthills();
        ArrayList<Spot> spots = graph.getSpots();

        AntFactory antFactory = new AntFactory(anthills[0], anthills[1]);
        Ant ant = antFactory.createAnt(AntType.Soldier);
        Ant ant2 = antFactory.createAnt(AntType.Soldier);
        Ant ant3 = antFactory.createAnt(AntType.Soldier);
        Ant ant4 = antFactory.createAnt(AntType.Soldier);
        Ant ant5 = antFactory.createAnt(AntType.Soldier);
        // Add ant to the graph
        Thread antThread = new Thread(ant);
        Thread antThread2 = new Thread(ant2);
        Thread antThread3 = new Thread(ant3);
        Thread antThread4 = new Thread(ant4);
        Thread antThread5 = new Thread(ant5);

        antThread.start();
        antThread2.start();
        antThread3.start();
        antThread4.start();
        antThread5.start();


        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

// Bug while adding neighbors to the graph
// 13 and 27 are connected, however 27 and 13 have no path
