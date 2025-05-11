package com.rebelalliance.quasar.domain.model;

public class DecodedMessage {
    private final ShipLocation position;
    private final String message;

    public DecodedMessage(ShipLocation position, String message) {
        if (position == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be null or empty");
        }

        this.position = position;
        this.message = message;
    }

    public ShipLocation getPosition() {
        return position;
    }

    public String getMessage() {
        return message;
    }
}