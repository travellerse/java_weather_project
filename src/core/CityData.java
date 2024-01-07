package core;

public class CityData {
    public String city;
    public String stage;
    public String country;
    public Double lat;
    public Double lon;

    public CityData(String city, String stage, String country, Double lat, Double lon) {
        this.city = city;
        this.stage = stage;
        this.country = country;
        this.lat = lat;
        this.lon = lon;
    }

    public CityData(String data) {
        String[] cityData = data.split(",");
        this.city = cityData[0];
        this.stage = cityData[1];
        this.country = cityData[2];
        this.lat = Double.parseDouble(cityData[3]);
        this.lon = Double.parseDouble(cityData[4]);
    }

    public String getCity() {
        return this.city + "," + this.stage + "," + this.country + "," + this.lat + "," + this.lon;
    }
}