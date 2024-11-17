package com.example.mpgexchange.util;

public class ModelMPG {

    private int year;
    private String modelName;
    private String mpg;

    public ModelMPG(int year, String modelName, String mpg) {
        this.year = year;
        this.modelName = modelName;
        this.mpg = mpg;
    }

    // Getters and Setters
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getMpg() {
        return mpg;
    }

    public void setMpg(String mpg) {
        this.mpg = mpg;
    }

    @Override
    public String toString() {
        return "Year: " + year + ", Model: " + modelName + ", MPG: " + mpg;
    }
}
