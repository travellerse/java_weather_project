package core;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FutureWeatherData extends WeatherData {

    public DataSet[] dataSet = new DataSet[5];

    public FutureWeatherData(CityData cityData) throws IOException, URISyntaxException {
        super(cityData, "forecast");
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
            int index = getDateDifference(firstDate, dt_txt);
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
        }
    }

    public int[] getMaxTemperature() {
        int[] maxTemperature = new int[5];
        for (int i = 0; i < 5; i++) {
            maxTemperature[i] = dataSet[i].maxTemperature;
        }
        return maxTemperature;
    }

    public int[] getMinTemperature() {
        int[] minTemperature = new int[5];
        for (int i = 0; i < 5; i++) {
            minTemperature[i] = dataSet[i].minTemperature;
        }
        return minTemperature;
    }

    public int getDateDifference(String begin, String end) {
        DateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        int index = -1;
        try {
            Date star = dft.parse(begin);
            Date endDay = dft.parse(end);
            Long starTime = star.getTime();
            Long endTime = endDay.getTime();
            long num = endTime - starTime;
            index = (int) (num / 24 / 60 / 60 / 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return index;
    }

    public static class DataSet {
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