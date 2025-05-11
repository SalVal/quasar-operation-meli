package com.rebelalliance.quasar.application;

import com.rebelalliance.quasar.domain.model.*;
import com.rebelalliance.quasar.domain.exceptions.*;
import com.rebelalliance.quasar.domain.ports.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class MessageProcessorTest {

    private LocationProvider locationProvider;
    private MessageDecoder messageDecoder;
    private MessageProcessor processor;

    @BeforeEach
    void setUp() {
        locationProvider = mock(LocationProvider.class);
        messageDecoder = mock(MessageDecoder.class);
        processor = new MessageProcessor(locationProvider, messageDecoder);
    }

    @Test
    void testProcessMessagesSuccess() throws Exception {
        // Configurar mocks
        when(locationProvider.getLocation(anyList()))
                .thenReturn(new ShipLocation(-100.0, 75.5));
        when(messageDecoder.decodeMessage(anyList()))
                .thenReturn("este es un mensaje secreto");

        // Ejecutar
        DecodedMessage result = processor.processMessages(
                List.of(
                        new SatelliteMessage("kenobi", 100.0, new String[]{}),
                        new SatelliteMessage("skywalker", 115.5, new String[]{}),
                        new SatelliteMessage("sato", 142.7, new String[]{})
                )
        );

        // Verificar
        assertEquals(-100.0, result.getPosition().getX());
        assertEquals(75.5, result.getPosition().getY());
        assertEquals("este es un mensaje secreto", result.getMessage());
    }

    @Test
    void testProcessMessagesWithLocationError() throws LocationException {
        when(locationProvider.getLocation(anyList()))
                .thenThrow(new LocationException("Error de ubicación"));

        assertThrows(LocationException.class, () ->
                processor.processMessages(List.of(
                        new SatelliteMessage("kenobi", 100.0, new String[]{}),
                        new SatelliteMessage("skywalker", 115.5, new String[]{}),
                        new SatelliteMessage("sato", 142.7, new String[]{})
                ))
        );
    }
}