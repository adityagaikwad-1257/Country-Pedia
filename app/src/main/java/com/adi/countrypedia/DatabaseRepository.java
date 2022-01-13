package com.adi.countrypedia;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.adi.countrypedia.db.CountryDao;
import com.adi.countrypedia.db.CountryDatabase;
import com.adi.countrypedia.models.Country;

import java.util.List;

public class DatabaseRepository {

    public CountryDao countryDao;
    private LiveData<List<Country>> countryList;

    public DatabaseRepository(Context context){
        CountryDatabase countryDatabase = CountryDatabase.getCountryDatabase(context);
        countryDao = countryDatabase.countryDao();
        countryList = countryDao.getCountryList();
    }

    public void insertCountries(List<Country> countries){
        new InsertTask(countryDao).execute(countries);
    }

    public LiveData<List<Country>> getCountryList(){
        return countryList;
    }

    public void deleteAll(){
        new DeleteAllTask(countryDao).execute();
    }

    private static class InsertTask extends AsyncTask<List<Country>, Void, Void>{
        private CountryDao countryDao;

        public InsertTask(CountryDao countryDao){
            this.countryDao = countryDao;
        }

        @Override
        protected Void doInBackground(List<Country>... lists) {
            countryDao.insertCountries(lists[0]);
            return null;
        }
    }

    private static class DeleteAllTask extends AsyncTask<Void, Void, Void>{
        private CountryDao countryDao;

        public DeleteAllTask(CountryDao countryDao){
            this.countryDao = countryDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            countryDao.deleteAllCountries();
            return null;
        }
    }

}
