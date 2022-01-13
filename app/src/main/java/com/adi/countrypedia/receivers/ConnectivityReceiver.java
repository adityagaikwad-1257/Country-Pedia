package com.adi.countrypedia.receivers;

import static com.adi.countrypedia.utils.Utils.isOnline;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.adi.countrypedia.DatabaseRepository;
import com.adi.countrypedia.api.CountryPlaceHolderApi;
import com.adi.countrypedia.models.Country;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectivityReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("aditya", "onReceive: ");
        if (isOnline(context))
            callServer(context);
    }

    private void callServer(Context context){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://restcountries.com/v3.1/region/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CountryPlaceHolderApi api = retrofit.create(CountryPlaceHolderApi.class);
        Call<List<Country>> call = api.getCountries();

        call.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                if (!response.isSuccessful()){
                    Log.d("aditya", "onResponse: fail " + response.code());
                    return;
                }

                List<Country> countryList = response.body();
                DatabaseRepository repository = new DatabaseRepository(context);
                repository.insertCountries(countryList);
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                Log.d("aditya", "onFailure: " + t.getMessage());
            }
        });

    }

}
