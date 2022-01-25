package com.example.btechquiz.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.btechquiz.model.ProgressModel
import com.example.btechquiz.utility.HelperQuestions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProgressViewModel(id: String, testsNo: String) : ViewModel() {
    val data = arrayListOf<ProgressModel>()
    var show=MutableLiveData<Int>()
    private val testNames= arrayListOf<String>()
    private val catId = id
    private val testNumber = testsNo

  init {
        val fireStore = FirebaseFirestore.getInstance()
        data.clear()
        fireStore.collection("USERS").document(FirebaseAuth.getInstance().currentUser!!.uid).collection("USER_SCORE").document(catId)
        fireStore.collection("CATEGORY").document(catId).collection("TESTS").document("TESTS_INFO")
            .get().addOnSuccessListener { snapshots ->
                val tests = testNumber.toInt()
                for (i in 1..tests) {
                    testNames.add(snapshots.getString("TEST" + i.toString() + "_ID")!!)
                }
                val userMap= hashMapOf<String,Int>()
                fireStore.collection("USERS").document(FirebaseAuth.getInstance().uid!!)
                    .collection("USER_SCORE").document(catId).get().addOnSuccessListener {
                        for(i in 0 until testNumber.toInt())
                        {
                            if(it.getLong(testNames[i])==null)
                            {
                                userMap[testNames[i]] = 0
                            }
                            else{
                            userMap[testNames[i]] = it.getLong(testNames[i])!!.toInt()
                        }}
                        for (i in 1..tests){
                            data.add(
                                ProgressModel(
                                    snapshots.getString("TEST" + i.toString() + "_ID")!!,
                                    userMap[ snapshots.getString("TEST" + i.toString() + "_ID")!!]!!,
                                    snapshots.getLong("TEST" + i.toString() + "_TIME")!!.toInt()
                                )
                            )
                        }
                        HelperQuestions.progressList=data

                        show.value=2
                    }
                    .addOnFailureListener {
                        show.value=4
                    }


            }
            .addOnFailureListener {
                show.value=3
            }


    }
}