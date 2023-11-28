package com.example.demo1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;

public class AntsWorldGraphApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Ants World app!");

        Pane pane = new Pane();
        Scene scene = new Scene(pane, 1200, 700);

        // Create a graph
        System.out.println(pane.getWidth());
        Graph graph = new Graph(30, pane);
        graph.drawOnPane();

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

