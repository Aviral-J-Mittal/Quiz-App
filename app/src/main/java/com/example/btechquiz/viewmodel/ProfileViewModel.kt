package com.example.btechquiz.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileViewModel : ViewModel() {
    var name = MutableLiveData<String>()
    var moveTo = MutableLiveData<String>()


    fun onSaveClicked() {
        when {
            name.value.isNullOrEmpty() -> {
                moveTo.value = "empty"
            }
            else -> {

                moveTo.value="set"
            }
        }
    }
}