package com.example.quoets.data.presentations

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quoets.data.ApiStatus
import com.example.quoets.data.repo.QuoteRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(private val quoteRepo: QuoteRepo):ViewModel() {

    val response: MutableState<ApiStatus> = mutableStateOf(ApiStatus.Empty)

    fun fetchQuotes() = viewModelScope.launch {
        quoteRepo.getQuotes()
            .onStart {
                response.value = ApiStatus.Loading
            }.catch {
                response.value = ApiStatus.Failure(it)
            }.collect{
                response.value = ApiStatus.Success(it)
            }
    }
 }