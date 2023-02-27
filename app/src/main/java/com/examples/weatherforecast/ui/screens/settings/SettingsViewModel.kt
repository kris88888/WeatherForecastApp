package com.examples.weatherforecast.ui.screens.settings

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examples.weatherforecast.data.database.Unit
import com.examples.weatherforecast.data.database.UnitDao
import com.examples.weatherforecast.repository.WeatherDatabaseRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repo: WeatherDatabaseRepo) : ViewModel() {

    private val _metricList =
        MutableStateFlow<List<com.examples.weatherforecast.data.database.Unit>>(
            emptyList()
        )

    val metricList = _metricList.asStateFlow()

    init {
        getUnit()
    }

    fun addUnit(unit: com.examples.weatherforecast.data.database.Unit) {
        viewModelScope.launch {
            repo.addUnit(unit)
        }
    }

    fun deleteUnit(unit: com.examples.weatherforecast.data.database.Unit) {
        viewModelScope.launch {
            repo.deleteUnit(unit)
        }
    }

    fun deleteAllUnits() {
        viewModelScope.launch {
            repo.deleteAllUnits()
        }
    }

    fun getUnit() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getUnit().distinctUntilChanged().collect {
                _metricList.value = it
            }
        }
    }

    fun updateUnit(unit: com.examples.weatherforecast.data.database.Unit) {
        viewModelScope.launch {
            repo.updateUnit(unit)
        }
    }

}