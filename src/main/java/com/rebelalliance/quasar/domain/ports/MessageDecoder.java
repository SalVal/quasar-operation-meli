package com.rebelalliance.quasar.domain.ports;

import com.rebelalliance.quasar.domain.model.SatelliteMessage;
import com.rebelalliance.quasar.domain.exceptions.MessageException;
import java.util.List;

public interface MessageDecoder {

    String decodeMessage(List<SatelliteMessage> messages) throws MessageException;
}
