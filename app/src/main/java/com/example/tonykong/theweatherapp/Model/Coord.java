package com.example.tonykong.theweatherapp.Model;

/**
 * Created by TonyKong on 12/26/2016.
 */

public class Coord {
    private double lon;
    private double lat;

    public Coord(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
