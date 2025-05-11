package com.rebelalliance.quasar.domain.model;

public class SatellitePosition {
    private final String name;
    private final ShipLocation position;

    public SatellitePosition(String name, double x, double y) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Satellite name cannot be null or empty");
        }
        this.name = name.toLowerCase();
        this.position = new ShipLocation(x, y);
    }

    public String getName() {
        return name;
    }

    public ShipLocation getPosition() {
        return position;
    }
}