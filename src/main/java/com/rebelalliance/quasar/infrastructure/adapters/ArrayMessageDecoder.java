package com.rebelalliance.quasar.infrastructure.adapters;

import com.rebelalliance.quasar.domain.ports.MessageDecoder;
import com.rebelalliance.quasar.domain.model.SatelliteMessage;
import com.rebelalliance.quasar.domain.exceptions.MessageException;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class ArrayMessageDecoder implements MessageDecoder {

    @Override
    public String decodeMessage(List<SatelliteMessage> messages) throws MessageException {

        if (messages == null || messages.isEmpty()) {
            throw new MessageException("There are no messages to decode");
        }

        int maxLength = messages.stream()
                .mapToInt(msg -> msg.getMessage().length)
                .max()
                .orElse(0);

        StringBuilder decodedMessage = new StringBuilder();
        for (int i = 0; i < maxLength; i++) {
            String word = findWordAtPosition(messages, i);
            if (!word.isEmpty()) {
                if (decodedMessage.length() > 0) decodedMessage.append(" ");
                decodedMessage.append(word);
            }
        }

        if (decodedMessage.isEmpty()) {
            throw new MessageException("The message could not be reconstructed.");
        }

        return decodedMessage.toString();
    }

    private String findWordAtPosition(List<SatelliteMessage> messages, int position) {
        for (SatelliteMessage msg : messages) {
            if (position < msg.getMessage().length
                    && !msg.getMessage()[position].isEmpty()) {
                return msg.getMessage()[position];
            }
        }
        return "";
    }
}
