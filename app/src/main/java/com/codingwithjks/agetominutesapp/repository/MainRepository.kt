package com.codingwithjks.agetominutesapp.repository

import android.app.DatePickerDialog
import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MainRepository @Inject constructor(
) {

    @ExperimentalCoroutinesApi
    fun getDatePicker(context: Context): Flow<List<Int>> = callbackFlow {
        val myCalendar = Calendar.getInstance()
        val mYear = myCalendar.get(Calendar.YEAR)
        val mMonth = myCalendar.get(Calendar.MONTH)
        val mDay = myCalendar.get(Calendar.DAY_OF_MONTH)

        object : DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                offer(listOf(dayOfMonth, month, year))
            }, mYear, mMonth, mDay
        ) {

        }.show()

        awaitClose {
        }
    }

    fun ageToMinute(date:List<Int>) : Flow<Long> = flow{

        val selectedDate = "${date[0]}/${date[1]}/${date[2]}"
        val sdf = SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH)
        val theDate = sdf.parse(selectedDate)
        val selectedDateInMin = theDate!!.time/60000
        val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
        val currentDateToMin = currentDate!!.time/60000
        val diffInMin = currentDateToMin - selectedDateInMin
        emit(diffInMin)
    }.flowOn(Dispatchers.IO)
}

