package core;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;

public class AirPolutionData extends WeatherData {
    public double co;
    public double no;
    public double no2;
    public double o3;
    public double so2;
    public double pm2_5;
    public double pm10;
    public int AQI;
    public String mainPollution;

    public AirPolutionData(String city) throws IOException, URISyntaxException {
        super(city, "air_pollution");
    }

    public int calculateAQI() {
        AQI = 0;
        mainPollution = "";
        int[] iaqi = new int[6];
        iaqi[0] = calculateIAQI(co, new double[]{0, 5, 10, 35, 60, 90, 120, 150});
        iaqi[1] = calculateIAQI(no2, new double[]{0, 100, 200, 700, 1200, 2340, 3090, 3840});
        iaqi[2] = calculateIAQI(o3, new double[]{0, 160, 200, 300, 400, 800, 1000, 1200});
        iaqi[3] = calculateIAQI(so2, new double[]{0, 150, 500, 650, 800, 1600, 2100, 2620});
        iaqi[4] = calculateIAQI(pm2_5, new double[]{0, 35, 75, 115, 150, 250, 350, 500});
        iaqi[5] = calculateIAQI(pm10, new double[]{0, 50, 150, 250, 350, 420, 500, 600});

        String[] mainPollutionList = new String[]{"CO", "NO2", "O3", "SO2", "PM2.5", "PM10"};
        for (int i = 0; i < iaqi.length; i++) {
            if (iaqi[i] > AQI) {
                AQI = iaqi[i];
                mainPollution = mainPollutionList[i];
            } else if (iaqi[i] == AQI) {
                mainPollution += "," + mainPollutionList[i];
            }
        }
        return AQI;
    }

    private int calculateIAQI(double pollutantConcentration, double[] breakList) {
        for (int i = 0; i < breakList.length - 1; i++) {
            int I_low = i * 50;
            int I_high = (i + 1) * 50;
            double C_low = breakList[i];
            double C_high = breakList[i + 1];
            if (pollutantConcentration >= breakList[i] && pollutantConcentration < breakList[i + 1]) {
                return (int) ((I_high - I_low) / (C_high - C_low) * (pollutantConcentration - C_low) + I_low);
            }
        }
        return 500;
    }

    public String getModeratoryPolluted() {
        if (AQI <= 50) {
            return "优";
        } else if (AQI <= 100) {
            return "良";
        } else if (AQI <= 150) {
            return "轻度污染";
        } else if (AQI <= 200) {
            return "中度污染";
        } else if (AQI <= 300) {
            return "重度污染";
        } else {
            return "严重污染";
        }
    }

    @Override
    public void analyzeWeatherData() {
        JSONArray list = data.getJSONArray("list");
        JSONObject jsonObject = list.getJSONObject(0);
        this.co = jsonObject.getJSONObject("components").getDouble("co") / 1000;
        this.no = jsonObject.getJSONObject("components").getDouble("no");
        this.no2 = jsonObject.getJSONObject("components").getDouble("no2");
        this.o3 = jsonObject.getJSONObject("components").getDouble("o3");
        this.so2 = jsonObject.getJSONObject("components").getDouble("so2");
        this.pm2_5 = jsonObject.getJSONObject("components").getDouble("pm2_5");
        this.pm10 = jsonObject.getJSONObject("components").getDouble("pm10");
    }
}