package com.example.prappmobcorrectviews.Classes.DatabaseClasses;

import java.util.Date;

public class Sample {

    private int sample_id;
    private double value;
    private Date timestamp;
    private int sensor_id;

    public Sample(int sample_id, double value, Date timestamp, int sensor_id) {
        this.sample_id = sample_id;
        this.value = value;
        this.timestamp = timestamp;
        this.sensor_id = sensor_id;
    }

    public int getSample_id() {
        return sample_id;
    }

    public void setSample_id(int sample_id) {
        this.sample_id = sample_id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getSensor_id() {
        return sensor_id;
    }

    public void setSensor_id(int sensor_id) {
        this.sensor_id = sensor_id;
    }
}
