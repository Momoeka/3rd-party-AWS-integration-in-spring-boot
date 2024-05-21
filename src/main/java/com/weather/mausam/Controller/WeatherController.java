package com.weather.mausam.Controller;

import java.sql.Time;
import java.text.DecimalFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WeatherController {

    @Value("${keypair}")
    private String key;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper ObjectMapper;

    @PostMapping("/getweather")
    public String getCurrentTempString(@RequestParam String cityName, Model model) {
        // return apiKey + " " + cityName;
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName
                + "&appid=" + key;
        String apiResponse = restTemplate.getForObject(url, String.class);
        JsonNode apiJSON;
        try {
            apiJSON = ObjectMapper.readTree(apiResponse);
            double tempKelvin = apiJSON.path("main").path("temp").asDouble();
            // System.out.println(tempKelvin);
            double tempCelcius = tempKelvin - 273.15;
            String celcius = String.format("%.2f", tempCelcius);
            tempCelcius = Double.parseDouble(celcius);
            // System.out.println(tempCelcius);

            int humidity = apiJSON.path("main").path("humidity").asInt();

            double windSpeed = apiJSON.path("wind").path("speed").asDouble();

            long dateNum = apiJSON.path("dt").asLong();
            Date currentDate = new Date(dateNum * 1000);

            String cloudCondition = apiJSON.path("weather").get(0).path("main").asText();

            String country = apiJSON.path("sys").path("country").asText();

            model.addAttribute("cityName", cityName);
            model.addAttribute("humidity", humidity);
            model.addAttribute("cloudCondition", cloudCondition);
            model.addAttribute("windSpeed", windSpeed);
            model.addAttribute("date", currentDate);
            model.addAttribute("temp", tempCelcius);
            model.addAttribute("country", country);

            return "dashboard";
        } catch (Exception e) {
            // TODO: handle exception
            return apiResponse;
        }

    }

    @PostMapping("/getweatherwithlocation")
    public String getWeatherWithLocation(@RequestParam String latitude, String longitude, Model model) {
        // return apiKey + " " + cityName;
        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid="
                + key;
        String apiResponse = restTemplate.getForObject(url, String.class);
        JsonNode apiJSON;
        String cityName;
        try {
            apiJSON = ObjectMapper.readTree(apiResponse);
            cityName = apiJSON.path("name").asText();
            double tempKelvin = apiJSON.path("main").path("temp").asDouble();
            // System.out.println(tempKelvin);
            double tempCelcius = tempKelvin - 273.15;
            String celcius = String.format("%.2f", tempCelcius);
            tempCelcius = Double.parseDouble(celcius);
            // System.out.println(tempCelcius);

            int humidity = apiJSON.path("main").path("humidity").asInt();

            double windSpeed = apiJSON.path("wind").path("speed").asDouble();

            long dateNum = apiJSON.path("dt").asLong();
            Date currentDate = new Date(dateNum * 1000);

            String cloudCOndition = apiJSON.path("weather").get(0).path("main").asText();

            model.addAttribute("cityName", cityName);
            model.addAttribute("humidity", humidity);
            model.addAttribute("cloudCondition", cloudCOndition);
            model.addAttribute("windSpeed", windSpeed);
            model.addAttribute("date", currentDate);
            model.addAttribute("temp", tempCelcius);

            return "dashboard";

        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }

    }

    @GetMapping("/loop")
    public String getMethodName(Model model) {
        int[] arr = { 1, 2, 3, 4, 5, 6 };
        model.addAttribute("array", arr);
        return "loop";
    }

}