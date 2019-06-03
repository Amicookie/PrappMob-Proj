package com.example.prappmobcorrectviews.Classes;

import com.example.prappmobcorrectviews.Classes.DatabaseClasses.Sensor;
import com.example.prappmobcorrectviews.Classes.DatabaseClasses.Workstation;

import java.util.Date;

public class Filter {
    private Date dateFrom;
    private Date dateTo;
    private Workstation workstation;
    private Sensor sensor;


    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
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
