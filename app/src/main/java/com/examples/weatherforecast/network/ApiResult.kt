package com.examples.weatherforecast.network

import java.lang.Exception

class ApiResult<T, Boolean, E : java.lang.Exception>(
    var data: T? = null,
    var loading: kotlin.Boolean = false,
    var exception: Exception? = null
)
