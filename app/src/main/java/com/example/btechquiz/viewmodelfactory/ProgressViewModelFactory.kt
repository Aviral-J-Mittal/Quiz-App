package com.example.btechquiz.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.btechquiz.viewmodel.ProgressViewModel

class ProgressViewModelFactory(private val id: String,private val testsNo:String) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
return ProgressViewModel(id,testsNo) as T
    }
}