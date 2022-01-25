package com.example.btechquiz.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.btechquiz.model.QuestionModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class BookMarkViewModel : ViewModel() {
    val bookMarkList= arrayListOf<QuestionModel>()
    var success: LiveData<String>?=null


            init{
                success= liveData {
                    val fireStore=FirebaseFirestore.getInstance()
                    bookMarkList.clear()
                    try {
                        val shot = fireStore.collection("USERS")
                            .document(FirebaseAuth.getInstance().uid!!).collection("USER_SCORE")
                            .document("BOOKMARKS").get().await()
                        val bookMap = shot.data
                        for (i in bookMap!!.values) {


                                   val snapshot=fireStore.collection("QUESTIONS").document(i.toString()).get()
                                       .await()

                                   bookMarkList.add(
                                       QuestionModel(
                                           snapshot.id,
                                           snapshot.getString("Question")!!,
                                           snapshot.getString("A)")!!,
                                           snapshot.getString("B)")!!,
                                           snapshot.getString("C)")!!,
                                           snapshot.getString("D)")!!,
                                           snapshot.getString("Answer")!!,
                                           "unselected",
                                           1
                                       )
                                   )






                        }
                        emit("successful")
                    }
                    catch (e:Exception) {
                        emit("unsuccessful")
                    }

                    }

                }

            }












