package com.example.demo1;

import javafx.scene.paint.Color;

public abstract class RedAnt extends Ant {
    public RedAnt(Anthill antHome, String name, int strength, int health) {
        super(antHome, Color.RED, name, strength, health);
    }
}
