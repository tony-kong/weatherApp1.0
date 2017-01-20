package com.example.tonykong.theweatherapp.Model;

/**
 * Created by TonyKong on 12/26/2016.
 */

public class Wind {
    private double speed;
    private double deg;

    public Wind(double speed, double deg) {
        this.speed = speed;
        this.deg = deg;
    }

    public double getDeg() {
        return deg;
    }

    public void setDeg(double deg) {
        this.deg = deg;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public String getDirection(){
        double deg = getDeg();
        String dir = "";
       if (deg>=0 && deg<=360){
           //numbers from wind direction & degree chart
           if (deg>=326.25 && deg<=33.75) dir = "N";
           if (deg > 33.75 && deg < 56.25) dir = "NE";
           if (deg >= 56.25 && deg <= 101.25) dir = "E";
           if (deg > 101.25 && deg < 146.25) dir = "SE";
           if (deg >= 146.25 && deg <= 191.25) dir = "S";
           if (deg > 191.25 && deg < 236.25) dir = "SW";
           if (deg >= 236.25 && deg <= 281.25) dir = "W";
           if (deg > 281.25 && deg < 326.25) dir = "NW";
       }
        return dir;
    }
}
