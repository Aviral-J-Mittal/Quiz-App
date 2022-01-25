package com.example.btechquiz.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.btechquiz.adapter.BookMarkAdapter
import com.example.btechquiz.databinding.ActivityBookMarkBinding
import com.example.btechquiz.viewmodel.BookMarkViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class BookMarkActivity : AppCompatActivity() {
    private var mActivityBookMarkBinding:ActivityBookMarkBinding?=null
    var mBookMarkViewModel:BookMarkViewModel?=null
    var adapter:BookMarkAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityBookMarkBinding = ActivityBookMarkBinding.inflate(layoutInflater)
        setContentView(mActivityBookMarkBinding !!.root)
        mActivityBookMarkBinding!!.progress.visibility = View.VISIBLE
        mBookMarkViewModel= ViewModelProvider(this).get(
            BookMarkViewModel::class.java)
        mBookMarkViewModel!!.success!!.observe(this, {
            if(it=="successful")
            {
                mActivityBookMarkBinding!!.progress.visibility = View.INVISIBLE
                val linear = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                mActivityBookMarkBinding!!.bookRecycler.layoutManager = linear
                val adapter = BookMarkAdapter(mBookMarkViewModel!!.bookMarkList,this)
                mActivityBookMarkBinding !!.bookRecycler.adapter = adapter
            }
            else{
                mActivityBookMarkBinding!!.progress.visibility = View.INVISIBLE
                Toast.makeText(this,"failed to load data!",Toast.LENGTH_SHORT).show()
            }
        })




        val swipeRecycler=object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                    val bookDoc= FirebaseFirestore.getInstance().collection("USERS")
                        .document(FirebaseAuth.getInstance().uid!!).collection("USER_SCORE")
                        .document("BOOKMARKS")
                    bookDoc.update(mBookMarkViewModel!!.bookMarkList[viewHolder.bindingAdapterPosition].queID, FieldValue.delete())


            }
        }
        val touchHelper=ItemTouchHelper(swipeRecycler)
        touchHelper.attachToRecyclerView(mActivityBookMarkBinding !!.bookRecycler)
    }
}