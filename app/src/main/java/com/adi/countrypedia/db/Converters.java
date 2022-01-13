package com.adi.countrypedia.db;

import androidx.room.TypeConverter;

import com.adi.countrypedia.models.CountryName;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class Converters {

    public static String getPopulation(int pop){
        StringBuilder str = new StringBuilder("");
        char[] chars = String.valueOf(pop).toCharArray();
        int i = chars.length;
        for (char c: chars){
            str.append(c);
            if (i % 2 == 0 && i != 2)
                str.append(",");
            i--;
        }
        return String.valueOf(str);
    }

    public static String getFromHash(HashMap<String, String> hashMap){
        if (hashMap == null)
            return "none";

        int i = 0;
        StringBuilder str = new StringBuilder("");
        for (String lan: hashMap.values()){
            str.append(lan);

            if (i < hashMap.size()-1)
                str.append(", ");
            i++;
        }

        return String.valueOf(str);
    }

    @TypeConverter
    public static String getFlags(HashMap<String, String> flags){
        if (flags == null)
            return null;

        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, String>>() {
        }.getType();
        String json = gson.toJson(flags, type);
        return json;
    }

    @TypeConverter
    public static HashMap<String, String> getFlagsHash(String flags){
        if (flags == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, String>>() {
        }.getType();
        HashMap<String, String> flagsHash = gson.fromJson(flags, type);
        return flagsHash;
    }

    @TypeConverter
    public static String getCapital(ArrayList<String> capitals){
        if (capitals == null)
            return null;

        String str = "";
        for (int i = 0; i < capitals.size(); i++) {
            str = str.concat(capitals.get(i) + " ");
            if (i<capitals.size()-1)
                str = str.concat(", ");
        }
        return str;
    }

    @TypeConverter
    public static ArrayList<String> getCapitalArray(String str){
        ArrayList<String> capitals = new ArrayList<>();
        capitals.add(str);
        return capitals;
    }

    @TypeConverter
    public static String getName(CountryName countryName){
        return countryName == null ? null : countryName.getCommon();
    }

    @TypeConverter
    public static CountryName getCountryName(String common){
        return new CountryName(common);
    }

}
