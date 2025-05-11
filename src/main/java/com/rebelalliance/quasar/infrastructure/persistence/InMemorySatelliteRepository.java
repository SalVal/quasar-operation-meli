package com.rebelalliance.quasar.infrastructure.persistence;

import com.rebelalliance.quasar.domain.ports.SatelliteDataRepository;
import com.rebelalliance.quasar.domain.model.SatelliteMessage;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemorySatelliteRepository implements SatelliteDataRepository {

    private final Map<String, SatelliteMessage> storage = new ConcurrentHashMap<>();

    @Override
    public void save(SatelliteMessage message) {
        storage.put(message.getSatelliteName().toLowerCase(), message);
    }

    @Override
    public Optional<SatelliteMessage> findBySatelliteName(String name) {
        return Optional.ofNullable(storage.get(name.toLowerCase()));
    }

    @Override
    public List<SatelliteMessage> findAll() {
        return new ArrayList<>(storage.values());
    }
}
