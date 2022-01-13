package com.adi.countrypedia.api;

import com.adi.countrypedia.models.Country;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CountryPlaceHolderApi {

    @GET("asia")
    Call<List<Country>> getCountries();
}
