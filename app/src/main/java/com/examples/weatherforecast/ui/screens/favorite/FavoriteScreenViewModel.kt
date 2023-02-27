package com.examples.weatherforecast.ui.screens.favorite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examples.weatherforecast.data.database.Favorite
import com.examples.weatherforecast.repository.WeatherDatabaseRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteScreenViewModel @Inject constructor(private val databaseRepo: WeatherDatabaseRepo) :
    ViewModel() {

    private val TAG = "FavViewModel"
    private val _favList = MutableStateFlow<List<Favorite>>(emptyList())

    val favList = _favList.asStateFlow()

    init {
        getFavorites()
    }

    fun isFavorite(city: String): Boolean {
        val isFavorite = _favList.value.filter { it.city.equals(city) }.isEmpty()
        Log.d(TAG, "$city favorite = $isFavorite")
        return isFavorite
    }

    fun getFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepo.getFavorites().distinctUntilChanged().collect {
                if (it.isNullOrEmpty()) {
                    Log.d(TAG, "list is empty")
                } else {
                    _favList.value = it
                }
            }
        }
    }

    fun addFavorite(city: String, country: String) {
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepo.insertFavorites(Favorite(city, country))
            Log.d(TAG, "ADDED $city, $country")
        }
    }

    fun updateFavorite(city: String, country: String) {
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepo.updateFavorites(Favorite(city, country))
        }
    }

    fun deleteFavorite(city: String, country: String) {
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepo.deleteFavorite(Favorite(city, country))
        }
    }

    fun deleteAllFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepo.deleteAllFavorites()
        }
    }
}