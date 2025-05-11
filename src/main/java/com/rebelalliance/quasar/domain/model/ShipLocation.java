package com.rebelalliance.quasar.domain.model;

public class ShipLocation {
    private final double x;
    private final double y;

    public ShipLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public ShipLocation toPoint() {
        return new ShipLocation(x, y);
    }
}