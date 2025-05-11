package com.rebelalliance.quasar.domain.model;

import java.util.Arrays;

public class SatelliteMessage {
    private final String satelliteName;
    private final double distance;
    private final String[] message;

    public SatelliteMessage(String satelliteName, double distance, String[] message) {
        if (satelliteName == null || satelliteName.trim().isEmpty()) {
            throw new IllegalArgumentException("Satellite name cannot be null or empty");
        }
        if (distance <= 0) {
            throw new IllegalArgumentException("Distance must be positive");
        }
        if (message == null) {
            throw new IllegalArgumentException("Message array cannot be null");
        }

        this.satelliteName = satelliteName.toLowerCase();
        this.distance = distance;
        this.message = message;
    }

    public String getSatelliteName() {
        return satelliteName;
    }

    public double getDistance() {
        return distance;
    }

    public String[] getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return String.format("SatelliteMessage[name=%s, distance=%.2f, message=%s]",
                satelliteName, distance, Arrays.toString(message));
    }
}