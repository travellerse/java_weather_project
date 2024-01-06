package core;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
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

    public void downloadIcon(String iconId) {
        String path = "resources/image/" + iconId + "@2x.png";
        String url= "https://openweathermap.org/img/wn/" + iconId + "@2x.png";
        if(Path.of(path).toFile().exists()){
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
            System.out.println(url);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Utils utils = new Utils();
        utils.downloadIcon("01n");
    }

    public int getDateDifference(String begin,String end){
        DateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        int index = -1;
        try {
            Date star = dft.parse(begin);
            Date endDay = dft.parse(end);
            Long starTime = star.getTime();
            Long endTime = endDay.getTime();
            Long num = endTime - starTime;
            index = (int) (num / 24 / 60 / 60 / 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return index;
    }

    public boolean checkInternetConnection() {
        try {
            URL url = new URL("https://www.baidu.com");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.connect();
            return connection.getResponseCode() == 200;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
