package com.rebelalliance.quasar.domain.ports;

import com.rebelalliance.quasar.domain.model.SatelliteMessage;
import java.util.List;
import java.util.Optional;

public interface SatelliteDataRepository {

    void save(SatelliteMessage message);

    Optional<SatelliteMessage> findBySatelliteName(String name);

    List<SatelliteMessage> findAll();
}