package com.example.btechquiz.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.btechquiz.viewmodel.QuestionsViewModel

class QuestionsViewModelFactory(private val id:String,private val tid:String): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return QuestionsViewModel(id,tid) as T
    }
}