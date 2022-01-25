package com.example.btechquiz.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage


class MainViewModel(application: Application) : AndroidViewModel(application) {
    var currentInte= MutableLiveData<Int>()
    var image=MutableLiveData<Uri>()
init {
    val ref= FirebaseStorage.getInstance().reference.child("images").child(FirebaseAuth.getInstance().uid!!)
    ref.downloadUrl.addOnSuccessListener {
        image.value=it
    }
}

    fun logOutIntpo()
    {
        currentInte.value=2
    }

}