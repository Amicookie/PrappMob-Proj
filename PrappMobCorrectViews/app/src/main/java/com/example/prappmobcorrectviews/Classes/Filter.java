package com.example.prappmobcorrectviews.Classes;

import com.example.prappmobcorrectviews.Classes.DatabaseClasses.Sensor;
import com.example.prappmobcorrectviews.Classes.DatabaseClasses.Workstation;

import java.time.LocalDateTime;
import java.util.Date;

public class Filter {
    private LocalDateTime dateFrom = null;
    private LocalDateTime dateTo = null;
    private Workstation workstation = null;
    private Sensor sensor = null;


    public LocalDateTime getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDateTime dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDateTime getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDateTime dateTo) {
        this.dateTo = dateTo;
    }

    public Workstation getWorkstation() {
        return workstation;
    }

    public void setWorkstation(Workstation workstation) {
        this.workstation = workstation;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
