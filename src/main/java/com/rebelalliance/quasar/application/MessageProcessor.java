package com.rebelalliance.quasar.application;

import com.rebelalliance.quasar.domain.ports.LocationProvider;
import com.rebelalliance.quasar.domain.ports.MessageDecoder;
import com.rebelalliance.quasar.domain.model.DecodedMessage;
import com.rebelalliance.quasar.domain.model.SatelliteMessage;
import com.rebelalliance.quasar.domain.exceptions.LocationException;
import com.rebelalliance.quasar.domain.exceptions.MessageException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageProcessor {

    private final LocationProvider locationProvider;
    private final MessageDecoder messageDecoder;

    public MessageProcessor(LocationProvider locationProvider, MessageDecoder messageDecoder) {
        this.locationProvider = locationProvider;
        this.messageDecoder = messageDecoder;
    }


    public DecodedMessage processMessages(List<SatelliteMessage> messages)
            throws LocationException, MessageException {

        if (messages == null || messages.size() < 3) {
            throw new LocationException("Se requieren datos de al menos 3 satélites");
        }

        var position = locationProvider.getLocation(messages);

        var message = messageDecoder.decodeMessage(messages);

        return new DecodedMessage(position, message);
    }
}