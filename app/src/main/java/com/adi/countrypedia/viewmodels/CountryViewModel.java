package com.adi.countrypedia.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.adi.countrypedia.DatabaseRepository;
import com.adi.countrypedia.models.Country;

import java.util.List;

public class CountryViewModel extends AndroidViewModel {
    DatabaseRepository repository;
    LiveData<List<Country>> countryList;

    public CountryViewModel(@NonNull Application application) {
        super(application);
        repository = new DatabaseRepository(application);
        countryList = repository.getCountryList();
    }

    public LiveData<List<Country>> getCountryList(){
        return countryList;
    }

    public void deleteAll(){
        repository.deleteAll();
    }
}
