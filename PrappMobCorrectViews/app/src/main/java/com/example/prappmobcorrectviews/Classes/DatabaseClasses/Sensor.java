package com.example.prappmobcorrectviews.Classes.DatabaseClasses;

public class Sensor {

    private int sensor_id;
    private String sensor_name;
    private String sensor_description;
    private int station_id;

    public Sensor(int sensor_id, String sensor_name, String sensor_description, int station_id){
        this.sensor_id = sensor_id;
        this.sensor_name = sensor_name;
        this.sensor_description = sensor_description;
        this.station_id = station_id;
    }

    public int getSensor_id() {
        return sensor_id;
    }

    public void setSensor_id(int sensor_id) {
        this.sensor_id = sensor_id;
    }

    public String getSensor_name() {
        return sensor_name;
    }

    public void setSensor_name(String sensor_name) {
        this.sensor_name = sensor_name;
    }

    public String getSensor_description() {
        return sensor_description;
    }

    public void setSensor_description(String sensor_description) {
        this.sensor_description = sensor_description;
    }

    public int getStation_id() {
        return station_id;
    }

    public void setStation_id(int station_id) {
        this.station_id = station_id;
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "sensor_id=" + sensor_id +
                ", sensor_name='" + sensor_name + '\'' +
                ", sensor_description='" + sensor_description + '\'' +
                ", station_id=" + station_id +
                '}';
    }
}
