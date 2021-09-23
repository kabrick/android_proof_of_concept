package com.website.health.models;

public class PatientVitals {
    private int id;
    private int heart_rate;
    private int resp_rate;
    private int temp;

    public PatientVitals( int id, int heart_rate, int resp_rate, int temp) {
        this.id = id;
        this.heart_rate = heart_rate;
        this.resp_rate = resp_rate;
        this.temp = temp;
    }

    public String getId() {
        return String.valueOf(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeartRate() {
        return String.valueOf(heart_rate);
    }

    public void setHeartRate(int heart_rate) {
        this.heart_rate = heart_rate;
    }

    public String getRespRate() {
        return String.valueOf(resp_rate);
    }

    public void setRespRate(int resp_rate) {
        this.resp_rate = resp_rate;
    }

    public String getTemp() {
        return String.valueOf(temp);
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }
}
