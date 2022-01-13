package com.adi.countrypedia.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.adi.countrypedia.models.Country;

import java.util.List;

@Dao()
public interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCountries(List<Country> countries);

    @Query("SELECT * FROM countries_table ORDER BY name ASC")
    LiveData<List<Country>> getCountryList();

    @Query("DELETE FROM countries_table")
    void deleteAllCountries();

}
