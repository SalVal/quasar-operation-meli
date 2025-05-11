package com.rebelalliance.quasar.application.controllers;

import com.rebelalliance.quasar.application.SplitMessageProcessor;
import com.rebelalliance.quasar.domain.model.DecodedMessage;
import com.rebelalliance.quasar.domain.model.SatelliteMessage;
import com.rebelalliance.quasar.domain.exceptions.LocationException;
import com.rebelalliance.quasar.domain.exceptions.MessageException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topsecret_split")
public class TopSecretSplitController {

    private final SplitMessageProcessor splitMessageProcessor;

    @Autowired
    public TopSecretSplitController(SplitMessageProcessor splitMessageProcessor) {
        this.splitMessageProcessor = splitMessageProcessor;
    }
    @Operation(
            summary = "Endpoint protegido",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @PostMapping("/{satellite_name}")
    public ResponseEntity<Void> addSatelliteData(
            @PathVariable("satellite_name") String satelliteName,
            @RequestBody SatelliteDataRequest request) {

        splitMessageProcessor.addSatelliteData(
                new SatelliteMessage(
                        satelliteName,
                        request.getDistance(),
                        request.getMessage().toArray(new String[0])
                )
        );
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> getDecodedMessage() {
        try {
            DecodedMessage result = splitMessageProcessor.processAllData();
            return ResponseEntity.ok(result);
        } catch (LocationException | MessageException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DTO
    public static class SatelliteDataRequest {
        private double distance;
        private List<String> message;

        // Getters y Setters
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
}