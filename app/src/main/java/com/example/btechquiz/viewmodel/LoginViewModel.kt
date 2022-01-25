package com.example.btechquiz.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class LoginViewModel(application: Application):AndroidViewModel(application) {
    var userMail=MutableLiveData<String>()
    var userPass=MutableLiveData<String>()
    var goTo=MutableLiveData<Int>()
    var googleSign=MutableLiveData<Int>()
    var moveTo=MutableLiveData<Int>()
    fun onTextClicked()
    {
        goTo.value=1
    }
    fun onLoginClicked()
    {
        when
        {
            userMail.value.isNullOrEmpty() || userPass.value.isNullOrEmpty()->
            {
                moveTo.value=3
            }
            else ->
            {
                moveTo.value=1
            }
        }



    }
    fun onGoogleClicked()
    {
        googleSign.value=1
    }
}