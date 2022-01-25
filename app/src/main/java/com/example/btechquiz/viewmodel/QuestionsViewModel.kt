package com.example.btechquiz.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.btechquiz.model.QuestionModel
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class QuestionsViewModel(id: String, tid: String) : ViewModel() {
    private val catId = id
    private val testId = tid
    var handleEvent=MutableLiveData<Int>()
    val questionList = arrayListOf<QuestionModel>()
    init {
        FirebaseFirestore.getInstance().collection("QUESTIONS").whereEqualTo("Category", catId)
            .whereEqualTo("Test", testId).get().addOnSuccessListener {
                for (doc: DocumentSnapshot in it) {
                    questionList.add(
                        QuestionModel(
                            doc.id,
                            doc.getString("Question")!!,
                            doc.getString("A)")!!,
                            doc.getString("B)")!!,
                            doc.getString("C)")!!,
                            doc.getString("D)")!!,
                            doc.getString("Answer")!!,
                            "unselected",
                                1
                        )
                    )
                }
                handleEvent.value=2
            }
            .addOnFailureListener {
                handleEvent.value=3
            }
    }
}