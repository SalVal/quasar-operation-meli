package com.rebelalliance.quasar.infrastructure.adapters;

import com.rebelalliance.quasar.domain.ports.LocationProvider;
import com.rebelalliance.quasar.domain.model.*;
import com.rebelalliance.quasar.domain.exceptions.LocationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Component
public class TrilaterationLocationProvider implements LocationProvider {

    private static final Map<String, SatellitePosition> KNOWN_SATELLITES = Map.of(
            "kenobi", new SatellitePosition("kenobi", -500, -200),
            "skywalker", new SatellitePosition("skywalker", 100, -100),
            "sato", new SatellitePosition("sato", 500, 100)
    );

    @Override
    public ShipLocation getLocation(List<SatelliteMessage> messages) throws LocationException {

        if (messages == null || messages.size() != 3) {
            throw new LocationException("Exactly 3 satellites are required");
        }

        for (SatelliteMessage msg : messages) {
            if (!KNOWN_SATELLITES.containsKey(msg.getSatelliteName().toLowerCase())) {
                throw new LocationException("Unknown satellite: " + msg.getSatelliteName());
            }
        }

        SatellitePosition kenobi = KNOWN_SATELLITES.get(messages.get(0).getSatelliteName().toLowerCase());
        double r1 = messages.get(0).getDistance();

        SatellitePosition skywalker = KNOWN_SATELLITES.get(messages.get(1).getSatelliteName().toLowerCase());
        double r2 = messages.get(1).getDistance();

        SatellitePosition sato = KNOWN_SATELLITES.get(messages.get(2).getSatelliteName().toLowerCase());
        double r3 = messages.get(2).getDistance();

        ShipLocation p1 = kenobi.getPosition();
        ShipLocation p2 = skywalker.getPosition();
        ShipLocation p3 = sato.getPosition();

        double A = 2 * (p2.getX() - p1.getX());
        double B = 2 * (p2.getY() - p1.getY());
        double C = Math.pow(r1, 2) - Math.pow(r2, 2)
                - Math.pow(p1.getX(), 2) + Math.pow(p2.getX(), 2)
                - Math.pow(p1.getY(), 2) + Math.pow(p2.getY(), 2);

        double D = 2 * (p3.getX() - p2.getX());
        double E = 2 * (p3.getY() - p2.getY());
        double F = Math.pow(r2, 2) - Math.pow(r3, 2)
                - Math.pow(p2.getX(), 2) + Math.pow(p3.getX(), 2)
                - Math.pow(p2.getY(), 2) + Math.pow(p3.getY(), 2);

        double x = (C * E - F * B) / (E * A - B * D);
        double y = (C * D - A * F) / (B * D - A * E);

        return new ShipLocation(x, y);
    }
}