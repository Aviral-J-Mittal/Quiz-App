package com.example.btechquiz.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.btechquiz.model.RankModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class LeaderViewModel : ViewModel() {
    var result: LiveData<String>? = null
    val data = arrayListOf<RankModel>()
    var userRank=MutableLiveData("N/A")


    init {
        result = liveData {
            try{
                val snapShot=FirebaseFirestore.getInstance().collection("USERS").whereGreaterThan("TOTAL_SCORE", 0)
                    .orderBy("TOTAL_SCORE", Query.Direction.DESCENDING).limit(20).get().await()
                var rank=1
                for(doc in snapShot)
                {
                    if (FirebaseAuth.getInstance().uid==doc.id)
                    {
                        userRank.value=rank.toString()
                    }
                    val ref= FirebaseStorage.getInstance().reference.child("images").child(doc.id)
                    try {
                        val imgUrl=ref.downloadUrl.await()
                        data.add(RankModel(doc.getString("NAME")!!,doc.getLong("TOTAL_SCORE")!!.toInt(),rank,imgUrl))
                        rank++
                    }
                    catch (e:Exception){
                        val uri = Uri.parse("android.resource://com.example.btechquiz/drawable/ic_baseline_person_24")
                        data.add(RankModel(doc.getString("NAME")!!,doc.getLong("TOTAL_SCORE")!!.toInt(),rank,uri))
                        rank++
                    }


                }

                emit("successful")
            }
            catch(e:Exception){
                emit("unsuccessful")
            }

        }
    }


    }
