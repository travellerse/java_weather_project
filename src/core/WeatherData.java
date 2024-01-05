package core;

public class WeatherData {
    public static String API_KEY = "bbd32e6d5eb4b5d48946266f604ddfe7";
    public String date;
    public String city;
    public String lat;
    public String lon;

    public void changeCity(String city) {
        this.city = city;
        //change lat and lon
    }
}
