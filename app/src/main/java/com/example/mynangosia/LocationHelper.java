package com.example.mynangosia;

public class LocationHelper {

    private double Longitude;
    private double Latitude ;

    public LocationHelper() {
    }

    public LocationHelper(double longitude, double latitude) {
        setLongitude(longitude);
        setLatitude(latitude);
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }
}
