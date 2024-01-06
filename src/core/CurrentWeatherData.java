package core;

import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;

public class CurrentWeatherData extends WeatherData {

    public String weather;
    public int temperature;
    public String wind;
    public String humidity;
    public String clouds;
    public String iconId;

    public CurrentWeatherData(String city) throws IOException, URISyntaxException {
        super(city, "weather");
    }

    @Override
    public void analyzeWeatherData() {
        this.weather = data.getJSONArray("weather").getJSONObject(0).getString("description");
        this.temperature = (int) data.getJSONObject("main").getDouble("temp");
        this.wind = String.valueOf(data.getJSONObject("wind").getDouble("speed"));
        this.humidity = String.valueOf(data.getJSONObject("main").getDouble("humidity"));
        this.clouds = String.valueOf(data.getJSONObject("clouds").getDouble("all"));
        this.iconId = data.getJSONArray("weather").getJSONObject(0).getString("icon");
    }
}