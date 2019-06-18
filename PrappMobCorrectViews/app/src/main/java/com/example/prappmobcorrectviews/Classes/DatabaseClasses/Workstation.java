package com.example.prappmobcorrectviews.Classes.DatabaseClasses;

public class Workstation {

    private int station_id;
    private String station_name;
    private String station_description;

    public Workstation(int station_id, String station_name, String station_description) {
        this.station_id = station_id;
        this.station_name = station_name;
        this.station_description = station_description;
    }

    public int getStation_id() {
        return station_id;
    }

    public void setStation_id(int station_id) {
        this.station_id = station_id;
    }

    public String getStation_name() {
        return station_name;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }

    public String getStation_description() {
        return station_description;
    }

    public void setStation_description(String station_description) {
        this.station_description = station_description;
    }

    @Override
    public String toString() {
        return station_name;
    }

}
