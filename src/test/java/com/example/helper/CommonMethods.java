package com.example.helper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonMethods {

    public String refactoredUserFriendlyName(String label) {
        return label
                .trim()
                .toLowerCase()
                .replaceAll("[^a-z0-9]+", "_")
                .replaceAll("^_+|_+$", "");
    }

    public String getValueFromJson(String jsonString, String key) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonString);
            JsonNode valueNode = root.get(key);

            if (valueNode != null) {
                // If it's a simple value (string, int), return as text
                if (valueNode.isValueNode()) {
                    return valueNode.asText();
                } else {
                    // If it's an object or array, convert to string
                    return mapper.writeValueAsString(valueNode);
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("Failed to parse JSON: " + e.getMessage());
            return null;
        }
    }
}
