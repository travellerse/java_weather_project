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

public abstract class WeatherData {
    public static String API_KEY = "bbd32e6d5eb4b5d48946266f604ddfe7";
    public String date;
    public String city;
    public Double lat;
    public Double lon;
    public String weatherClass;
    public JSONObject data;

    public WeatherData(String city, String weatherClass) {
        this.date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
        this.city = city;
        this.weatherClass = weatherClass;
    }

    public void changeCity(String city) {
        this.city = city;
        //change lat and lon
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
            System.out.println(date);
        }
    }

    public abstract void analyzeWeatherData();

    public void getCityPosition() throws IOException, URISyntaxException {
        String weatherUrl = "https://api.openweathermap.org/geo/1.0/direct?q=" + city + "&limit=5&appid=" + API_KEY;
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
            this.lat = data.getDouble("lat");
            this.lon = data.getDouble("lon");
            System.out.println(this.lat);
            System.out.println(this.lon);
        }
    }

}
