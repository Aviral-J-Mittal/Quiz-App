package com.example.btechquiz.viewmodel

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.btechquiz.utility.SingleLiveEvent

class LaunchViewModel(application: Application) : AndroidViewModel(application) {
    var handler: Handler?=null
    var pos= MutableLiveData<Int>()
    private val _navigateToDetails = SingleLiveEvent<Any>()

    val navigateToDetails : LiveData<Any>
        get() = _navigateToDetails


    init {
        handler = Handler(Looper.getMainLooper())
        _navigateToDetails.call()

    }
    private val mRunnable:Runnable= Runnable {

        pos.value=1



    }
    fun splash()
    {
        handler?.postDelayed(mRunnable,2000)

    }}