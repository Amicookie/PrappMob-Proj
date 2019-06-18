package com.example.prappmobcorrectviews.Classes.DatabaseClasses;

import java.time.LocalDateTime;
import java.util.Date;

public class Sample {

    private int sample_id;
    private double value;
    private LocalDateTime timestamp;
    private int sensor_id;

    public Sample(int sample_id, double value, LocalDateTime timestamp, int sensor_id) {
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getSensor_id() {
        return sensor_id;
    }

    public void setSensor_id(int sensor_id) {
        this.sensor_id = sensor_id;
    }
}
