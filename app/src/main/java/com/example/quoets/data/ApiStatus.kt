package com.example.quoets.data

import com.example.quoets.data.models.QuoteModel
import retrofit2.Response

sealed class ApiStatus {

    class Success(val data:Response<QuoteModel>):ApiStatus()
    class Failure(val msg:Throwable):ApiStatus()
    data object Loading:ApiStatus()
    data object Empty:ApiStatus()
}