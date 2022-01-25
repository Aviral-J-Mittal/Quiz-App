package com.example.btechquiz.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.btechquiz.viewmodel.ScoreViewModel


class ScoreViewModelFactory(private val score:Int): ViewModelProvider.NewInstanceFactory()  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ScoreViewModel(score) as T
    }
}