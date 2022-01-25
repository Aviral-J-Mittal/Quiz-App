package com.example.btechquiz.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.btechquiz.viewmodel.TimerViewModel

class TimerViewModelFactory(private val catTime:Int): ViewModelProvider.NewInstanceFactory()  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TimerViewModel(catTime) as T
    }
}