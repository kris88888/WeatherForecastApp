package com.examples.weatherforecast.di

import android.content.Context
import androidx.room.Room
import com.examples.weatherforecast.data.database.DatabaseConstants
import com.examples.weatherforecast.data.database.UnitDao
import com.examples.weatherforecast.data.database.WeatherDao
import com.examples.weatherforecast.data.database.WeatherRoomDatabase
import com.examples.weatherforecast.network.WeatherApi
import com.examples.weatherforecast.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Dagger Hilt Module meant to define app level (Singleton) dependencies.
 */

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesWeatherApi(retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun providesRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesWeatherDao(weatherRoomDatabase: WeatherRoomDatabase): WeatherDao =
        weatherRoomDatabase.weatherDao()

    @Provides
    @Singleton
    fun providesUnitsDao(weatherRoomDatabase: WeatherRoomDatabase): UnitDao =
        weatherRoomDatabase.unitDao()

    @Provides
    @Singleton
    fun providesWeatherDatabase(@ApplicationContext context: Context): WeatherRoomDatabase =
        Room.databaseBuilder(context = context, WeatherRoomDatabase::class.java,
            DatabaseConstants.WEATHER_DB_NAME).fallbackToDestructiveMigration().build()
}