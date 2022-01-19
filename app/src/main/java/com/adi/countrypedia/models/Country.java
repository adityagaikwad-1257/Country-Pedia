package com.adi.countrypedia.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.HashMap;

@Entity(tableName = "countries_table")
public class Country {

    @NonNull
    @PrimaryKey
    private CountryName name;

    private ArrayList<String> capital;
    private HashMap<String, String> flags;
    private String region;
    private String subregion;
    private int population;
    private ArrayList<String> borders;
    private HashMap<String, String> languages;

    public CountryName getName() {
        return name; }

    public void setName(@NonNull CountryName name) {
        this.name = name;
    }

    public ArrayList<String> getCapital() {
        return capital;
    }

    public HashMap<String, String> getFlags() {
        return flags;
    }

    public String getRegion() {
        return region;
    }

    public String getSubregion() {
        return subregion;
    }

    public int getPopulation() {
        return population;
    }

    public ArrayList<String> getBorders() {
        return borders;
    }

    public HashMap<String, String> getLanguages() {
        return languages;
    }
}
