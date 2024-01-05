package core;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

class AirPolutionData extends WeatherData{
    public String date;
    public double co;
    public double no;
    public double no2;
    public double o3;
    public double so2;
    public double pm2_5;
    public double pm10;
    public double nh3;

    public AirPolutionData(String date) {
        this.date = date;
    }

    public int getAQI(){
        return 0;
    }

    public void getAirPolutionData() throws IOException, URISyntaxException {
        String airPolutionUrl = "https://api.openweathermap.org/data/2.5/air_pollution?lat=" + lat + "&lon=" + lon + "&appid=" + API_KEY;
        URL url = new URL(airPolutionUrl).toURI().toURL();
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        this.date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpsURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JSONObject json = new JSONObject(response.toString());
            JSONArray list = json.getJSONArray("list");
            JSONObject jsonObject = list.getJSONObject(0);
            this.co = jsonObject.getJSONObject("components").getDouble("co");
            this.no = jsonObject.getJSONObject("components").getDouble("no");
            this.no2 = jsonObject.getJSONObject("components").getDouble("no2");
            this.o3 = jsonObject.getJSONObject("components").getDouble("o3");
            this.so2 = jsonObject.getJSONObject("components").getDouble("so2");
            this.pm2_5 = jsonObject.getJSONObject("components").getDouble("pm2_5");
            this.pm10 = jsonObject.getJSONObject("components").getDouble("pm10");
            this.nh3 = jsonObject.getJSONObject("components").getDouble("nh3");
        }
    }
}