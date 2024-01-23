package com.example.quoets.data.repo

import com.example.quoets.data.models.QuoteModel
import com.example.quoets.data.services.QuoteService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class QuoteRepo @Inject constructor(private val quoteService: QuoteService) {

    fun getQuotes():Flow<Response<QuoteModel>> = flow {
        emit(quoteService.fetchQuote())
    }.flowOn(Dispatchers.IO)
}