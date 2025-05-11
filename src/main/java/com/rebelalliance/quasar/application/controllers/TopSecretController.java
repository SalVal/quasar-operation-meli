package com.rebelalliance.quasar.application.controllers;

import com.rebelalliance.quasar.application.MessageProcessor;
import com.rebelalliance.quasar.domain.model.DecodedMessage;
import com.rebelalliance.quasar.domain.model.SatelliteMessage;
import com.rebelalliance.quasar.domain.exceptions.LocationException;
import com.rebelalliance.quasar.domain.exceptions.MessageException;
import com.rebelalliance.quasar.domain.model.ShipLocation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/topsecret")
public class TopSecretController {

    private final MessageProcessor messageProcessor;

    public TopSecretController(MessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
    }

    @PostMapping
    public ResponseEntity<?> processMessage(@RequestBody TopSecretRequest request) {
        try {
            List<SatelliteMessage> messages = request.getSatellites().stream()
                    .map(s -> new SatelliteMessage(
                            s.getName(),
                            s.getDistance(),
                            s.getMessage().toArray(new String[0]))
                    )
                    .collect(Collectors.toList());

            DecodedMessage result = messageProcessor.processMessages(messages);
            return ResponseEntity.ok(new DecodedMessageResponse(result));
        } catch (LocationException | MessageException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DTOs
    public static class TopSecretRequest {
        private List<SatelliteRequest> satellites;

        // Getters y Setters
        public List<SatelliteRequest> getSatellites() {
            return satellites;
        }

        public void setSatellites(List<SatelliteRequest> satellites) {
            this.satellites = satellites;
        }
    }

    public static class SatelliteRequest {
        private String name;
        private double distance;
        private List<String> message;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public List<String> getMessage() {
            return message;
        }

        public void setMessage(List<String> message) {
            this.message = message;
        }
    }

    public static class DecodedMessageResponse {
        private final PointResponse position;
        private final String message;

        public DecodedMessageResponse(DecodedMessage decodedMessage) {
            this.position = new PointResponse(decodedMessage.getPosition());
            this.message = decodedMessage.getMessage();
        }

        public PointResponse getPosition() {
            return position;
        }

        public String getMessage() {
            return message;
        }
    }

    public static class PointResponse {
        private final double x;
        private final double y;

        public PointResponse(ShipLocation point) {
            this.x = point.getX();
            this.y = point.getY();
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }
}