package com.example.demo1;

enum AntType {
    Soldier, Collector, Blunderer, Worker, Drone
}

public class AntFactory {

    private Anthill redAntHill;
    private Anthill blueAntHill;

    public AntFactory(Anthill redAntHill, Anthill blueAntHill) {
        this.redAntHill = redAntHill;
        this.blueAntHill = blueAntHill;
    }

    public Ant createAnt(AntType antType) {
        switch (antType) {
            case Soldier: {
                String name = "Soldier" + (int)(Math.random() * 100);
                int strength = (int)(Math.random() * 10);
                int health = (int)(Math.random() * 10);
                return new Soldier(redAntHill, name, strength, health);}
            case Worker: {
                String name = "Worker" + (int)(Math.random() * 100);
                int strength = (int)(Math.random() * 10);
                int health = (int)(Math.random() * 10);
                return new Worker(blueAntHill, name, strength, health);}
            default:
                return null;
        }
    }
}