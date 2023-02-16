package com.examples.weatherforecast.practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.examples.weatherforecast.ui.theme.WeatherForecastTheme

class PracticeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherForecastTheme {
                // SideEffectExample()
                // CancelDetectLaunchedEffect()
                // SideEffectExample()
                // DisposableEffectExample()
                // JustLaunchEffect
                // produceStateExample()
               // RememberCoroutineScopeExample()
                // DerivedStateExample()
                TestSnapshotFlow()
            }
        }
    }
}