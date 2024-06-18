package core;

import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherData {
    public static String API_KEY = "YOUR_API_KEY";
    public String date;
    public String city;
    public String stage;
    public String country;
    public Double lat;
    public Double lon;
    public String weatherClass;
    public JSONObject data;

    public WeatherData(CityData cityData, String weatherClass) throws IOException, URISyntaxException {
        this.date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
        changeCity(cityData);
        this.weatherClass = weatherClass;
    }

    public WeatherData(CityData cityData) throws IOException, URISyntaxException {
        changeCity(cityData);
    }

    public void changeCity(CityData cityData) throws IOException, URISyntaxException {
        this.city = cityData.city;
        this.stage = cityData.stage;
        this.country = cityData.country;
        this.lat = cityData.lat;
        this.lon = cityData.lon;
    }

    public void getWeatherData() throws IOException, URISyntaxException {
        String weatherUrl = "https://api.openweathermap.org/data/2.5/" + weatherClass + "?lat=" + lat + "&lon=" + lon + "&units=metric&appid=" + API_KEY;
        URL url = new URL(weatherUrl).toURI().toURL();
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpsURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            data = new JSONObject(response.toString());
        }
    }

    public void analyzeWeatherData() {

    }

    public void getCityPosition() throws IOException, URISyntaxException {
        String cityUrl = "https://api.openweathermap.org/geo/1.0/direct?q=" + city + "&limit=5&appid=" + API_KEY;
        URL url = new URL(cityUrl).toURI().toURL();
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpsURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                inputLine = inputLine.replace("[", "");
                inputLine = inputLine.replace("]", "");
                response.append(inputLine);
            }
            in.close();
            data = new JSONObject(response.toString());
            this.lat = data.getDouble("lat");
            this.lon = data.getDouble("lon");
            this.city = data.getString("name");
            this.country = data.getString("country");
            this.stage = data.getString("state");
        }
    }
}
