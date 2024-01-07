package core;

import org.json.JSONArray;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static core.WeatherData.API_KEY;

public class WeatherServer {
    public CurrentWeatherData currentWeatherData;
    public FutureWeatherData futureWeatherData;
    public AirPolutionData airPolutionData;
    public CityData cityData = new CityData("Beijing", "Beijing", "CN", 39.9075, 116.3972);

    public static void main(String[] args) {
        String[] iconId = {"01d", "01n", "02d", "02n", "03d", "03n", "04d", "04n", "09d", "09n", "10d", "10n", "11d", "11n", "13d", "13n", "50d", "50n"};
        for (String s : iconId) {
            downloadIcon(s);
        }
    }

    public static void downloadIcon(String iconId) {
        String path = "resources/image/" + iconId + "@2x.png";
        String url = "https://openweathermap.org/img/wn/" + iconId + "@2x.png";
        if (Path.of(path).toFile().exists()) {
            return;
        }
        try {
            InputStream inputStream = new URL(url).openStream();
            OutputStream outputStream = new FileOutputStream(path);
            byte[] buffer = new byte[2048];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            inputStream.close();
            outputStream.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "网络已断开");
        }
    }

    public static String getWeekday(String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(dateString);

            SimpleDateFormat sdfWeekday = new SimpleDateFormat("EEEE");
            return sdfWeekday.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "Invalid Date";
        }
    }

    public void update() throws IOException, URISyntaxException {
        currentWeatherData.getWeatherData();
        currentWeatherData.analyzeWeatherData();
        futureWeatherData.getWeatherData();
        futureWeatherData.analyzeWeatherData();
        airPolutionData.getWeatherData();
        airPolutionData.analyzeWeatherData();
        airPolutionData.calculateAQI();
    }

    public void changeCity(CityData cityData) throws IOException, URISyntaxException {
        currentWeatherData.changeCity(cityData);
        futureWeatherData.changeCity(cityData);
        airPolutionData.changeCity(cityData);
        this.cityData = cityData;
        update();
    }

    public boolean checkInternetConnection() {
        try {
            URL url = new URL("https://www.baidu.com");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.connect();
            return connection.getResponseCode() == 200;
        } catch (IOException e) {
            return false;
        }
    }

    public String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm z");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    public boolean isIntegerHour() {
        SimpleDateFormat formatter = new SimpleDateFormat("mmss");
        Date date = new Date(System.currentTimeMillis());
        return Integer.parseInt(formatter.format(date)) == 0;
    }

    public void start(Long milliseconds) {
        while (currentWeatherData == null || futureWeatherData == null || airPolutionData == null) {
            try {
                currentWeatherData = new CurrentWeatherData(new CityData("Beijing", "Beijing", "CN", 39.9075, 116.3972));
                futureWeatherData = new FutureWeatherData(new CityData("Beijing", "Beijing", "CN", 39.9075, 116.3972));
                airPolutionData = new AirPolutionData(new CityData("Beijing", "Beijing", "CN", 39.9075, 116.3972));
                update();
            } catch (Exception e) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        new Thread(() -> {
            boolean isInternetConnected = checkInternetConnection();
            while (true) {
                try {
                    Thread.sleep(milliseconds);
                    if (isIntegerHour() || !isInternetConnected) {
                        update();
                        isInternetConnected = checkInternetConnection();
                    }
                } catch (InterruptedException | IOException | URISyntaxException e) {
                    if (isInternetConnected)
                        JOptionPane.showMessageDialog(null, "网络已断开");
                    isInternetConnected = checkInternetConnection();
                }
            }
        }).start();
    }

    public List getCityList(String name) throws IOException, URISyntaxException {
        List<CityData> cityDataList = new ArrayList<>();
        String cityUrl = "https://api.openweathermap.org/geo/1.0/direct?q=" + name + "&limit=5&appid=" + API_KEY;
        URL url = new URL(cityUrl).toURI().toURL();
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
            JSONArray data = new JSONArray(response.toString());
            int num = data.length();
            for (int i = 0; i < 3 && i < num; ++i) {
                cityDataList.add(new CityData(data.getJSONObject(i).getString("name"), data.getJSONObject(i).getString("state"), data.getJSONObject(i).getString("country"), data.getJSONObject(i).getDouble("lat"), data.getJSONObject(i).getDouble("lon")));
            }
        }
        return cityDataList;
    }

}
