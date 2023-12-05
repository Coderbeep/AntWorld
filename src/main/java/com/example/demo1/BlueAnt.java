package com.example.demo1;

import javafx.scene.paint.Color;

public abstract class BlueAnt extends Ant {

    public BlueAnt(Anthill antHome, String name, int strength, int health) {
        super(antHome, Color.BLUE, name, strength, health);
    }
}
