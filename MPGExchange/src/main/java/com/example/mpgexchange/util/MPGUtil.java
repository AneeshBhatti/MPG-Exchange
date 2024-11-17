package com.example.mpgexchange.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class MPGUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Get the list of vehicle models for a given year and make
    public static List<ModelMPG> getFuelEconomyData(String make, int year) {
        String url = "https://www.fueleconomy.gov/ws/rest/vehicle/menu/model?year=" + year + "&make=" + make;

        List<ModelMPG> models = new ArrayList<>();
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Accept", "application/json")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // Log the raw JSON response for debugging
                System.out.println("API Response: " + response.body());

                // Parse the JSON response
                JsonNode root = objectMapper.readTree(response.body());
                JsonNode menuItems = root.path("menuItem");

                // Loop through the items and fetch detailed data
                for (JsonNode menuItem : menuItems) {
                    String modelName = menuItem.path("text").asText();
                    String modelValue = menuItem.path("value").asText();

                    // Fetch detailed fuel economy data for the model
                    String mpg = fetchMpgDataForModel(modelValue);

                    // Add the model and MPG data to the list
                    models.add(new ModelMPG(year, modelName, mpg));
                }
            } else {
                System.out.println("Error: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return models;
    }

    // Fetch the detailed fuel economy data for a model
    private static String fetchMpgDataForModel(String modelValue) {
        String url = "https://www.fueleconomy.gov/ws/rest/vehicle/MPG?" + "value=" + modelValue;

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Accept", "application/json")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // Parse the detailed response to extract MPG
                JsonNode root = objectMapper.readTree(response.body());
                String cityMpg = root.path("city").asText();
                String highwayMpg = root.path("highway").asText();
                return "City: " + cityMpg + ", Highway: " + highwayMpg;
            } else {
                return "MPG data not available";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error fetching MPG data";
        }
    }
}
