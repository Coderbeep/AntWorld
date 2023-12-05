package com.example.demo1;

import javafx.scene.layout.Pane;

public class PaneManager {
    private static PaneManager instance;
    private Pane pane;

    private PaneManager() {}

    public static PaneManager getInstance() {
        if (instance == null) {
            instance = new PaneManager();
        }
        return instance;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public Pane getPane() {
        return pane;
    }
}
