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

    // creates jackson library object mapper used to parse JSON data
    private static final ObjectMapper objectMapper = new ObjectMapper();

    //method to retrieve fuel economy data for specific make and year
    public static List<ModelMPG> getFuelEconomyData(String make, int year) {
        //create the URL to retrieve data from
        String url = "https://www.fueleconomy.gov/ws/rest/vehicle/menu/model?year=" + year + "&make=" + make;

        List<ModelMPG> models = new ArrayList<>();

        try {
            //Create HTTP client and send request
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))   //set URL
                    .header("Accept", "application/json")   //Accept JSON data
                    .build();
            //send request and retrieve data as a string
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            //if the response is successful, returns 200
            if (response.statusCode() == 200) {
                // Log JSON data for debugging
                System.out.println("API Response: " + response.body());

                // Pass JSON data
                JsonNode root = objectMapper.readTree(response.body());
                JsonNode menuItems = root.path("menuItem");

                // Loop through returned models and retrieve MPG
                for (JsonNode menuItem : menuItems) {
                    String modelName = menuItem.path("text").asText();
                    String modelValue = menuItem.path("value").asText();

                    // retrieve MPG
                    String mpg = fetchMpgDataForModel(modelValue);

                    // create ModelMPG object and add it to models list
                    models.add(new ModelMPG(year, modelName, mpg));
                }
            } else {
                System.out.println("Error: " + response.statusCode());  //otherwise log error
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return models;
    }

    // method to retrieve MPG for specific model
    private static String fetchMpgDataForModel(String modelValue) {
        //build url
        String url = "https://www.fueleconomy.gov/ws/rest/vehicle/MPG?" + "value=" + modelValue;

        try {
            //Create client and send request
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))   //set url
                    .header("Accept", "application/json")   //accept JSON data
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
