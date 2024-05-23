package com.example.weatherapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET("weather")
    Call<WeatherApp> getWeather(
            @Query("q") String city,
            @Query("appid") String Appid,
            @Query("units") String Units
    );

}
