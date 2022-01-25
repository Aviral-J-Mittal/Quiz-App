package com.example.btechquiz.viewmodel

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import java.util.regex.Pattern

class RegisterViewModel(application: Application):AndroidViewModel(application) {
    val userMail = MutableLiveData<String>()
    val userPass = MutableLiveData<String>()
    val userName= MutableLiveData<String>()
    var error1= MutableLiveData<Int>()
    var error2=MutableLiveData<Int>()
    var passCheck1= MutableLiveData<Int>()
    var moveLogin= MutableLiveData<Int>()
    var passCheck2= MutableLiveData<Int>()
    var passCheck3= MutableLiveData<Int>()
    var passCheck4= MutableLiveData<Int>()
    private val pattern1: Pattern?= Pattern.compile("(.*[\$@\$!%*#?&].*)")
    private val pattern2: Pattern?= Pattern.compile("(.*[A-Z].*)")
    private val pattern3: Pattern?= Pattern.compile("(.*[0-9].*)")
    init {
        passCheck1.value=1
        passCheck2.value=1
        passCheck3.value=1
        passCheck4.value=1

    }



    fun onRegisterClicked()
    {
        when
        {
            userName.value.isNullOrEmpty() ->
            {
                error1.value=1
                error2.value=2
            }
            userMail.value.isNullOrEmpty() || !Patterns.EMAIL_ADDRESS.matcher(userMail.value.toString().trim())
                .matches() -> {
                error1.value=2
                error2.value=1
            }
            else -> {
                error1.value=3
                passCheck()
                if (passCheck1.value==2 && passCheck2.value==2 && passCheck3.value==2 && passCheck4.value==2)
                {

                  moveLogin.value=2
                    error1.value=2
                        error2.value=2


                }
                else
                {
                    moveLogin.value=1
                    error1.value=2
                    error2.value=2
                }
            }
        }

    }

    private fun passCheck() {
        if (userPass.value.toString().length >= 8) {
            passCheck1.value = 2
        } else {
            passCheck1.value = 1
        }
        if (pattern1!!.matcher(userPass.value.toString())
                .matches()
        ) {
            passCheck2.value = 2
        } else {
            passCheck2.value = 1
        }
        if (pattern2!!.matcher(userPass.value.toString())
                .matches()
        ) {
            passCheck3.value = 2
        } else {
            passCheck3.value = 1
        }
        if (pattern3!!.matcher(userPass.value.toString())
                .matches()
        ) {
            passCheck4.value = 2
        } else {
            passCheck4.value = 1
        }


    }




    }