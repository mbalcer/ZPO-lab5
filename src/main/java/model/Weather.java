package model;

public class Weather {
    private String city;
    private double temperature;
    private String weather;
    private double windSpeed;

    public Weather() {
    }

    public Weather(String city, double temperature, String weather, double windSpeed) {
        this.city = city;
        this.temperature = temperature;
        this.weather = weather;
        this.windSpeed = windSpeed;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }
}
