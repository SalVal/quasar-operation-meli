package com.rebelalliance.quasar.application;

import com.rebelalliance.quasar.domain.ports.SatelliteDataRepository;
import com.rebelalliance.quasar.domain.model.DecodedMessage;
import com.rebelalliance.quasar.domain.model.SatelliteMessage;
import com.rebelalliance.quasar.domain.exceptions.LocationException;
import com.rebelalliance.quasar.domain.exceptions.MessageException;
import org.springframework.stereotype.Component;


@Component
public class SplitMessageProcessor {

    private final SatelliteDataRepository repository;
    private final MessageProcessor messageProcessor;

    public SplitMessageProcessor(
            SatelliteDataRepository repository,
            MessageProcessor messageProcessor
    ) {
        this.repository = repository;
        this.messageProcessor = messageProcessor;
    }

    public void addSatelliteData(SatelliteMessage message) {
        repository.save(message);
    }

    public DecodedMessage processAllData() throws LocationException, MessageException {
        var messages = repository.findAll();

        if (messages.size() < 3) {
            throw new LocationException(
                    "No hay suficientes datos. Requeridos: 3, Actuales: " + messages.size()
            );
        }

        return messageProcessor.processMessages(messages);
    }
}