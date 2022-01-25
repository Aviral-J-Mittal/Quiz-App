package com.example.btechquiz.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.concurrent.TimeUnit

class TimerViewModel(catTime: Int) : ViewModel() {
    private var time: CountDownTimer? = null
    private val totalTime = (catTime * 60 * 1000).toLong()
    var currentTime= MutableLiveData<String>()
    var timeLeft=MutableLiveData<Long>()
    var finish=MutableLiveData<Boolean>()
    init {
        time = object : CountDownTimer(totalTime, 1000) {
            override fun onTick(p0: Long) {
                timeLeft.value=p0
                currentTime.value = TimeUnit.MILLISECONDS.toMinutes(p0)
                    .toString() + ":" + (TimeUnit.MILLISECONDS.toSeconds(p0) - TimeUnit.MINUTES.toSeconds(
                    TimeUnit.MILLISECONDS.toMinutes(p0)
                )).toString()
            }

            override fun onFinish() {
               finish.value=true
            }

        }
        time!!.start()
    }


    override fun onCleared() {
        super.onCleared()
        time!!.cancel()
    }
}