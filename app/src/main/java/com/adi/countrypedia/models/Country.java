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
    private final CountryName name;

    private final ArrayList<String> capital;
    private final HashMap<String, String> flags;
    private final String region;
    private final String subregion;
    private final int population;
    private final ArrayList<String> borders;
    private final HashMap<String, String> languages;

    public Country(CountryName name, ArrayList<String> capital, HashMap<String, String> flags, String region,
                   String subregion, int population, ArrayList<String> borders, HashMap<String, String> languages) {
        this.name = name;
        this.capital = capital;
        this.flags = flags;
        this.region = region;
        this.subregion = subregion;
        this.population = population;
        this.borders = borders;
        this.languages = languages;
    }

    public CountryName getName() {
        return name; }

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
