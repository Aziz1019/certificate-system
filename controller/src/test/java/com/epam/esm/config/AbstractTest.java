package com.epam.esm.config;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestControllerConfig.class)
public abstract class AbstractTest {

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        // For reading an object first that comes in json. Then we read what's inside
        JsonNode rootNode = objectMapper.readTree(json);
        JsonNode dataNode = rootNode.get("data");
        if (dataNode == null) {
            return objectMapper.readValue(json, clazz);
        }
        return objectMapper.readValue(dataNode.toString(), clazz);
    }

    protected <T> List<T> mapFromObjectJson(String json, Class<T> clazz) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);
        JsonNode dataNode = rootNode.get("data");
        if (dataNode == null) {
            throw new IllegalArgumentException("JSON string does not contain a 'data' node");
        }
        return objectMapper.readValue(dataNode.toString(), objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
    }
}
