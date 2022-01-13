package com.adi.countrypedia.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.adi.countrypedia.models.Country;

@Database(entities = {Country.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class CountryDatabase extends RoomDatabase {

    private static CountryDatabase countryDatabase;

    public abstract CountryDao countryDao();

    public static CountryDatabase getCountryDatabase(Context context){

        if (countryDatabase == null){
            countryDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    CountryDatabase.class,
                    "countries_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return countryDatabase;
    }
}
