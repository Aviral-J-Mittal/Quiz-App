package com.example.btechquiz.utility


import com.example.btechquiz.model.ProgressModel
import com.example.btechquiz.model.QuestionModel
import com.example.btechquiz.model.RankModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HelperQuestions {
    companion object {
        var queList: ArrayList<QuestionModel>? = null
        var progressList: ArrayList<ProgressModel>? = null
        var selectedTest: Int? = null
        var selectedCategoryID: String? = null
        var userName:String?=null


    }
}