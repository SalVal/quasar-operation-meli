package com.rebelalliance.quasar.domain.ports;

import com.rebelalliance.quasar.domain.model.SatelliteMessage;
import com.rebelalliance.quasar.domain.exceptions.LocationException;
import com.rebelalliance.quasar.domain.model.ShipLocation;

import java.util.List;

public interface LocationProvider {

    ShipLocation getLocation(List<SatelliteMessage> messages) throws LocationException;
}