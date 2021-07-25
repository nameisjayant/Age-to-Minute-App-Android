package com.codingwithjks.agetominutesapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.codingwithjks.agetominutesapp.databinding.ActivityMainBinding
import com.codingwithjks.agetominutesapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val mainViewModel: MainViewModel by viewModels()

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getDatePicker()
        ageToMin()
    }

    @SuppressLint("SetTextI18n")
    private fun ageToMin() {
        binding.apply {
            lifecycleScope.launchWhenStarted {
                mainViewModel.ageToMin.collect {
                    ageInMinutes.text = "$it"
                }
            }
        }
    }

    @ExperimentalCoroutinesApi
    @SuppressLint("SetTextI18n")
    private fun getDatePicker() {
        binding.apply {
            datePicker.setOnClickListener {
                mainViewModel.getDatePicker(this@MainActivity)
                lifecycleScope.launchWhenStarted {
                    mainViewModel.datePicker.collect {
                        mainViewModel.ageToMin(it)
                        selectedDate.text = "${it[0]}/${it[1]}/${it[2]}"
                    }
                }
            }
        }
    }
}