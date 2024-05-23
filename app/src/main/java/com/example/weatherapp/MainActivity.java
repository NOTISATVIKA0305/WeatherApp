package com.example.weatherapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Switch;

import java.lang.String;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.weatherapp.databinding.ActivityMainBinding;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    static ActivityMainBinding binding;
    private static Retrofit retrofit;
    private static Api api;
    private static WeatherApp data;

    private static Call<WeatherApp> response1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        fetchData("Nellore");
        searchView();
    }
    public  static void searchView(){
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query!=null)
                {
                    fetchData(query);
                }
                return true;

            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                return true;
            }
        });
    }

    public static void fetchData(String city) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.openweathermap.org/data/2.5/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        api = retrofit.create(Api.class);
        response1 = api.getWeather(city,"e7a8818a30558e7c2882294fd6ec2bf4", "metrics");
        response1.enqueue(new Callback<WeatherApp>() {
            @Override
            public void onResponse(Call<WeatherApp> call, Response<WeatherApp> response) {
                data = response.body();
                if (response.isSuccessful() && data != null) {
                    Double temperature = data.getMain().getTemp() - 273.15;

                    Long humidity = data.getMain().getHumidity();
                    Double speed = data.getWind().getSpeed();
                    Long sunrise = data.getSys().getSunrise();
                    Long sunset = data.getSys().getSunset();
                    Long sea_level = data.getMain().getSeaLevel();
                    Double max_temp = data.getMain().getTempMax() - 273.15;
                    Double min_temp = data.getMain().getTempMin() - 273.15;
                    Log.e("res", "Temperature: " + temperature);
                    DecimalFormat df = new DecimalFormat();
                    String roundedtem = df.format(temperature);
                    binding.temp.setText(String.valueOf(roundedtem) + "°C");
                    binding.humidity.setText(String.valueOf(humidity + "%"));
                    binding.windspeed.setText(String.valueOf(speed + "m/s"));
                    // Convert timestamps to LocalDateTime and format them
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

                    LocalDateTime sunriseDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(sunrise), ZoneId.systemDefault());
                    LocalDateTime sunsetDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(sunset), ZoneId.systemDefault());

                    String formattedSunrise = sunriseDateTime.toLocalTime().format(formatter);
                    String formattedSunset = sunsetDateTime.toLocalTime().format(formatter);

                    binding.sunrise.setText(formattedSunrise);
                    binding.sunset.setText(formattedSunset);
                    binding.sealevel.setText(String.valueOf(sea_level + "hpa"));
                    String r_max = df.format(max_temp);
                    String r_min = df.format(min_temp);
                    binding.max.setText(String.valueOf("MAX:" + r_max + "°C"));
                    binding.min.setText(String.valueOf("MIN:" + r_min + "°C"));
                    String con = data.getWeather().get(0).getMain();
                    binding.condition.setText(con);
                    binding.cityname.setText(data.getName());
                    LocalDate currentDate = LocalDate.now();
                    Log.d("res2", "time" + currentDate);
                    // Get current date
                    // Format the date as a String
                    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MM-YYYY");
                    String formattedDate = currentDate.format(formatter1);
                    DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
                    String dayName = dayOfWeek.name();
                    // Set the formatted date to the TextView using Data Binding
                    binding.currentdate.setText(formattedDate);
                    binding.day.setText(dayName);
                    switch (con) {
                        case "Sunny":
                        case "Clear Sky":
                        case "Clear ":
                            Log.d("WeatherCondition", "It's raining");
                            // Update UI for sunny
                            binding.lottieAnimationView.setAnimation(R.raw.sunny);
                            binding.main.setBackgroundResource(R.drawable.sunny_back);

                            break;
                        case "Clouds":
                        case "Party Clouds":
                        case "Over Cast":
                        case "Mist":
                        case "Foggy":
                            Log.d("WeatherCondition", "Clear sky");
                            // Update UI for rainy
                            binding.lottieAnimationView.setAnimation(R.raw.cloudyanimation);
                            binding.main.setBackgroundResource(R.drawable.cloudyback);
                            break;
                        case "Light Rainy":
                        case "Drizzle":
                        case "Moderate Rain":
                        case "Showers":
                        case "Heavy Rain":
                            binding.lottieAnimationView.setAnimation(R.raw.rainicon);
                            binding.main.setBackgroundResource(R.drawable.rainyback);
                            break;
                        case "Light Snow":
                        case "Moderate Snow":
                        case "Heavy Snow":
                            binding.lottieAnimationView.setAnimation(R.raw.snoeanimation);
                            binding.main.setBackgroundResource(R.drawable.snowback);

                            break;


                    }

                }
            }

            @Override
            public void onFailure(Call<WeatherApp> call, Throwable throwable) {

            }
        });

    }

}

