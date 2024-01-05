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

public class CurrentWeatherData extends WeatherData {

    private String weather;
    private int temperature;
    private String wind;
    private String humidity;
    private String clouds;
    private String iconId;

    private FutureWeatherData[] futureWeatherData = new FutureWeatherData[5];

    private AirPolutionData airPolutionData;

    public CurrentWeatherData(String lat, String lon) {
        this.date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
        this.lat = lat;
        this.lon = lon;
    }

    public static void main(String[] args) {
        System.out.println("https://api.openweathermap.org/data/2.5/forecast?lat=39.906217&lon=116.3912757&units=metric&appid=bbd32e6d5eb4b5d48946266f604ddfe7");
        CurrentWeatherData currentWeatherData = new CurrentWeatherData("39.906217", "116.3912757");
        try {
            getCurrentWeatherData();

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

    }

    public String getCity() {
        return city;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public String getWeather() {
        return weather;
    }

    public int getTemperature() {
        return temperature;
    }

    public String getWind() {
        return wind;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getClouds() {
        return clouds;
    }

    public String getIconId() {
        return iconId;
    }

    public void getCurrentWeatherData() throws IOException, URISyntaxException {
        String weatherUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&units=metric&appid=" + API_KEY;
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
            JSONObject json = new JSONObject(response.toString());
            this.weather = json.getJSONArray("weather").getJSONObject(0).getString("description");
            this.temperature = (int) json.getJSONObject("main").getDouble("temp");
            this.wind = String.valueOf(json.getJSONObject("wind").getDouble("speed"));
            this.humidity = String.valueOf(json.getJSONObject("main").getDouble("humidity"));
            this.clouds = String.valueOf(json.getJSONObject("clouds").getDouble("all"));
            this.iconId = json.getJSONArray("weather").getJSONObject(0).getString("icon");
            new Utils().downloadIcon(this.iconId);
        }
    }


    //天⽓状况图标，STEP 2中图⽚内容需要根据实时天⽓状况进⾏判断。利⽤未来5天天⽓预测API:
    //list.weather.icon 字段，选择对应的图标。请注意， list 是⼀个有序列表，包含了40个时间点的天⽓预
    //测。
    //对于Tonight来说，如果在 list 包含当地时间今天18:00之前的数据（包括18:00），就给出18:00前的
    //离18:00最近的时间点的预测数据：例如list中有今天17:00，20:00...的数据，就给出17:00的数据。如果
    //list 只有当地时间今天18:00之后的数据，就显示 Data Unavailbale ，字体任意，并且也不显示今晚
    //的最⾼温度和最低温度数据，以及曲线图。（3‘’）
    //对于未来4天来说，选择 list 中该⽇当地时间中午12:00的数据。如果没有当地时间中午12:00的数据，
    //就选择相邻的最近时间的数据：例如只有11:00和14:00的数据，就选择11:00的数据。（2‘’
    //最⼤温度和最⼩温度，STEP 2中利⽤未来5天天⽓预测API: list.main.temp 字段，给出最⾼温度和最低温度
    //的预测。
    //对于Tonight来说，如果在 list 中包含当地时间今天18:00之前的数据（包括18:00），就统计 list 中
    //时间范围在当地时间今天 24:00 前的数据（不包括24:00)，得到的最⼩值和最⼤值分别作为最⼩温度和
    //最⼤温度的预测。（3‘’）否则不显示今晚的最⾼温度和最低温度数据，以及曲线图。
    //对于未来4天来说，统计 list 中时间范围在该⽇当地时间 0:00-24:00 间的数据（包括00:00，不包括
    //24:00)，得到的最⼩值和最⼤值分别作为最⼩温度和最⼤温度的预测。
    public void getFutureWeatherData() throws IOException, URISyntaxException {
        Boolean isAfterTime = Boolean.TRUE;
        String forecastUrl = "https://api.openweathermap.org/data/2.5/forecast?lat=" + lat + "&lon=" + lon + "&units=metric&appid=" + API_KEY;
        URL url = new URL(forecastUrl).toURI().toURL();
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
            JSONObject json = new JSONObject(response.toString());
            JSONArray list = json.getJSONArray("list");
            date = list.getJSONObject(0).getString("dt_txt").split(" ")[0];
            for (int i = 0; i < list.length(); i++) {
                JSONObject jsonObject = list.getJSONObject(i);

                String dt_txt = jsonObject.getString("dt_txt").split(" ")[0];
                String time = jsonObject.getString("dt_txt").split(" ")[1];
                int hour = Integer.parseInt(time.split(":")[0]);
                if (dt_txt.equals(date) && hour < 18) {
                    isAfterTime = Boolean.FALSE;
                }
                int index = new Utils().getDateDifference(date, dt_txt);
                if (index >= 5) {
                    break;
                }
                if (futureWeatherData[index] == null)
                    futureWeatherData[index] = new FutureWeatherData(dt_txt);
                int Difference = 12;
                if (index == 0) {
                    Difference = 18;
                }
                if (Math.abs(hour - Difference) < futureWeatherData[index].timeDifference) {
                    futureWeatherData[index].timeDifference = Math.abs(hour - Difference);
                    futureWeatherData[index].iconId = jsonObject.getJSONArray("weather").getJSONObject(0).getString("icon");
                }
                futureWeatherData[index].maxTemperature = (int) Math.max(futureWeatherData[index].maxTemperature, jsonObject.getJSONObject("main").getDouble("temp"));
                futureWeatherData[index].minTemperature = (int) Math.min(futureWeatherData[index].minTemperature, jsonObject.getJSONObject("main").getDouble("temp"));
            }
            if (isAfterTime) {
                //Data Unavailable
                futureWeatherData[0].maxTemperature = -300;
                futureWeatherData[0].minTemperature = -300;
                futureWeatherData[0].iconId = "Data Unavailable";
            }
        }
    }


    class FutureWeatherData {
        public int maxTemperature;
        public int minTemperature;
        public String iconId;
        int timeDifference;
        private String date;

        public FutureWeatherData(String date) {
            this.date = date;
            this.maxTemperature = -273;
            this.minTemperature = 100;
            this.timeDifference = 100;
        }

        public String getDate() {
            return date;
        }
    }


}