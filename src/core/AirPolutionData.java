package core;

import org.json.JSONArray;
import org.json.JSONObject;

public class AirPolutionData extends WeatherData {
    public double co;
    public double no;
    public double no2;
    public double o3;
    public double so2;
    public double pm2_5;
    public double pm10;
    public double nh3;

    public AirPolutionData(String city) {
        super(city, "air_pollution");
    }

    public int getAQI() {
        return 0;
    }

    @Override
    public void analyzeWeatherData() {
        JSONArray list = data.getJSONArray("list");
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