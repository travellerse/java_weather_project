package core;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;

public class FutureWeatherData extends WeatherData {

    public DataSet dataSet[] = new DataSet[5];

    public FutureWeatherData(String city) throws IOException, URISyntaxException {
        super(city, "forecast");
    }

    @Override
    public void analyzeWeatherData() {
        dataSet = new DataSet[5];
        boolean isAfterTime = Boolean.TRUE;

        JSONArray list = data.getJSONArray("list");
        String firstDate = list.getJSONObject(0).getString("dt_txt").split(" ")[0];
        for (int i = 0; i < list.length(); i++) {
            JSONObject jsonObject = list.getJSONObject(i);

            String dt_txt = jsonObject.getString("dt_txt").split(" ")[0];
            String time = jsonObject.getString("dt_txt").split(" ")[1];
            int hour = Integer.parseInt(time.split(":")[0]);
            if (dt_txt.equals(firstDate) && hour < 18) {
                isAfterTime = Boolean.FALSE;
            }
            int index = new Utils().getDateDifference(firstDate, dt_txt);
            if (index >= 5) {
                break;
            }
            if (dataSet[index] == null)
                dataSet[index] = new DataSet(dt_txt);
            int Difference = 12;
            if (index == 0) {
                Difference = 18;
            }
            if (Math.abs(hour - Difference) < dataSet[index].timeDifference) {
                dataSet[index].timeDifference = Math.abs(hour - Difference);
                dataSet[index].iconId = jsonObject.getJSONArray("weather").getJSONObject(0).getString("icon");
            }
            dataSet[index].maxTemperature = (int) Math.max(dataSet[index].maxTemperature, jsonObject.getJSONObject("main").getDouble("temp"));
            dataSet[index].minTemperature = (int) Math.min(dataSet[index].minTemperature, jsonObject.getJSONObject("main").getDouble("temp"));
        }
        if (isAfterTime) {
            //Data Unavailable
            dataSet[0].maxTemperature = -300;
            dataSet[0].minTemperature = -300;
            dataSet[0].iconId = "Data Unavailable";
        }
    }

    class DataSet {
        public int maxTemperature;
        public int minTemperature;
        public String iconId;
        public int timeDifference;
        public String date;

        public DataSet(String date) {
            this.date = date;
            this.maxTemperature = -273;
            this.minTemperature = 100;
            this.timeDifference = 100;
        }
    }
}