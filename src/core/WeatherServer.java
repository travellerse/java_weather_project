package core;

import javax.net.ssl.HttpsURLConnection;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherServer {
    public CurrentWeatherData currentWeatherData;
    public FutureWeatherData futureWeatherData;
    public AirPolutionData airPolutionData;

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
            System.out.println(url);
            InputStream inputStream = new URL(url).openStream();
            OutputStream outputStream = new FileOutputStream(path);
            byte[] buffer = new byte[2048];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            inputStream.close();
            outputStream.close();
            System.out.println(url);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() throws IOException, URISyntaxException {
        currentWeatherData.getWeatherData();
        currentWeatherData.analyzeWeatherData();
        futureWeatherData.getWeatherData();
        futureWeatherData.analyzeWeatherData();
        airPolutionData.getWeatherData();
        airPolutionData.analyzeWeatherData();
    }

    public void start(Long milliseconds) {
        try {
            currentWeatherData = new CurrentWeatherData("Beijing");
            futureWeatherData = new FutureWeatherData("Beijing");
            airPolutionData = new AirPolutionData("Beijing");
            update();
        } catch (Exception e) {
            e.printStackTrace();
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
                    System.out.println("Internet Connection Error");
                    isInternetConnected = checkInternetConnection();
                }
            }
        }).start();
    }

    public void changeCity(String city) throws IOException, URISyntaxException {
        currentWeatherData.changeCity(city);
        futureWeatherData.changeCity(city);
        airPolutionData.changeCity(city);
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

}
