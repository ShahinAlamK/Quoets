package com.example.quoets.data.services

import com.example.quoets.data.models.QuoteModel
import com.example.quoets.data.utils.Api
import retrofit2.Response
import retrofit2.http.GET

interface QuoteService {

    @GET(Api.END_POINT)
    suspend fun fetchQuote():Response<QuoteModel>
}