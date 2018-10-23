package com.microsoft.azure.iotsolutions.devicetelemetry.services.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.iotsolutions.devicetelemetry.services.exceptions.InvalidInputException;
import com.microsoft.azure.iotsolutions.devicetelemetry.services.models.actions.EmailActionServiceModel;
import com.microsoft.azure.iotsolutions.devicetelemetry.services.models.actions.IActionServiceModel;
import com.microsoft.azure.iotsolutions.devicetelemetry.services.models.actions.IActionServiceModel.ActionType;

import java.io.IOException;
import java.util.*;

public class ActionDeserializer extends JsonDeserializer<IActionServiceModel> {
    private static final String TYPE = "Type";
    private static final String PARAMETERS = "Parameters";
    private static final String RECIPIENTS_KEY = "Recipients";

    @Override
    public IActionServiceModel deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec oc = parser.getCodec();
        JsonNode node = oc.readTree(parser);
        // default is email action
        IActionServiceModel result = new EmailActionServiceModel();
        ActionType type = ActionType.valueOf(node.get(TYPE).asText());

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> parameters = objectMapper.readValue(node.get(PARAMETERS).toString(), Map.class);

        switch (type) {
            // default to email action.
            // If more action types are added, this switch will grow
            default:
                parameters = this.deserializeEmailParameters(parameters);
                try {
                    result = new EmailActionServiceModel(parameters);
                } catch (InvalidInputException exception) {
                    //log??
                }
                break;
        }

        return result;
    }

    private Map<String, Object> deserializeEmailParameters(Map<String, Object> parameters) throws IOException {
        Map<String,Object> result = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        result.putAll(parameters);

        if (result.containsKey(RECIPIENTS_KEY) && result.get(RECIPIENTS_KEY) != null) {
            List<String> recipientsList = (ArrayList<String>)result.get(RECIPIENTS_KEY);
            result.put(RECIPIENTS_KEY, recipientsList);
        }
        return result;
    }
}
