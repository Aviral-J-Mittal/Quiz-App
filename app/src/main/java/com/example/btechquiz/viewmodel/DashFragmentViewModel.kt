package com.example.btechquiz.viewmodel


import androidx.collection.ArrayMap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.btechquiz.model.GridDes
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot


class DashFragmentViewModel : ViewModel() {
    var action = MutableLiveData<Int>()
    var categoryList = arrayListOf<GridDes>()

    init {
        val fireStore = FirebaseFirestore.getInstance()
        categoryList.clear()
        fireStore.collection("CATEGORY").get().addOnSuccessListener { snapshots ->
            val docList = ArrayMap<String, QueryDocumentSnapshot>()
            for (doc: QueryDocumentSnapshot in snapshots) {
                docList[doc.id] = doc
            }
            val listDoc = docList["Categories"]
            val catCount = listDoc!!.getLong("COUNT")
            for (i in 1..catCount!!) {
                val id = listDoc.getString("CAT" + i.toString() + "_ID")
                val docCat = docList[id]
                val tests = docCat!!.get("NO_OF_TESTS")
                val name = docCat.getString("NAME")
                categoryList.add(GridDes(id!!, name!!, tests.toString()))

            }
            action.value = 2


        }
            .addOnFailureListener {
                action.value = 3
            }



    }


}