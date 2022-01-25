package com.example.btechquiz.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.btechquiz.utility.HelperQuestions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await


class ScoreViewModel(score:Int):ViewModel() {
    var result:LiveData<String>?=null
    init {
        result= liveData {
           val fireStore=FirebaseFirestore.getInstance()
           val batch= fireStore.batch()
           val userDoc=fireStore.collection("USERS").document(FirebaseAuth.getInstance().uid!!)
           if(score>HelperQuestions.progressList!![HelperQuestions.selectedTest!!].score)
           {
               val scoreDoc=userDoc.collection("USER_SCORE").document(HelperQuestions.selectedCategoryID!!)
               val testMap= hashMapOf<String,Any>()
               testMap[HelperQuestions.progressList!![HelperQuestions.selectedTest!!].type] = score
               batch.set(scoreDoc,testMap, SetOptions.merge())
           }
           try {
               batch.commit().await()

                   fireStore.collection("USERS").document(FirebaseAuth.getInstance().uid!!)
                       .collection("USER_SCORE").get().addOnSuccessListener {
                           val scoreDocuments =it.documents
                           var sum=0
                           for(i in scoreDocuments)
                           {
                               if(i.id=="BOOKMARKS")
                               {
                                   continue
                               }
                               val userMap=i.data

                               for(m in userMap!!.values)
                               {
                                   sum += m.toString().toInt()
                               }
                               fireStore.collection("USERS").document(FirebaseAuth.getInstance().uid!!).update("TOTAL_SCORE",sum)

                           }
                       }



               emit("successful")
           }
           catch (e:Exception){
               emit("unsuccessful")
           }

       }
    }
}