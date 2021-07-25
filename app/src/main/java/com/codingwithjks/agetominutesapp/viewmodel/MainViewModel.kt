package com.codingwithjks.agetominutesapp.viewmodel

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingwithjks.agetominutesapp.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(private val mainRepository: MainRepository) : ViewModel() {

    private val _datePicker: MutableStateFlow<List<Int>> = MutableStateFlow(listOf(0,0,0))
    val datePicker: StateFlow<List<Int>> = _datePicker

    private val _ageToMin:MutableStateFlow<Long> = MutableStateFlow(0)
    val ageToMin:StateFlow<Long> = _ageToMin

    @ExperimentalCoroutinesApi
    fun getDatePicker(context: Context) = viewModelScope.launch {
        mainRepository.getDatePicker(context)
            .catch { e ->
                Log.d("main", "Exception: ${e.message} ")
            }.collect {
                _datePicker.value = it
            }
    }

    fun ageToMin(date:List<Int>)= viewModelScope.launch {
        mainRepository.ageToMinute(date)
            .catch { e->
                Log.d("main", "Exception: ${e.message} ")
            }.collect {
                _ageToMin.value = it
            }
    }
}