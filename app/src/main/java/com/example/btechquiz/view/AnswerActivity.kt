package com.example.btechquiz.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.btechquiz.adapter.AnswerAdapter
import com.example.btechquiz.adapter.ProgressAdapter
import com.example.btechquiz.databinding.ActivityAnswerBinding
import com.example.btechquiz.databinding.ActivityProgressBinding
import com.example.btechquiz.utility.HelperQuestions

class AnswerActivity : AppCompatActivity() {
    var mActivityAnswerBinding:ActivityAnswerBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityAnswerBinding = ActivityAnswerBinding.inflate(layoutInflater)
        setContentView(mActivityAnswerBinding !!.root)
        val linear = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mActivityAnswerBinding !!.progressRecycler.layoutManager = linear
        val adapter = AnswerAdapter(HelperQuestions.queList!!,this)
        mActivityAnswerBinding !!.progressRecycler.adapter = adapter
    }
}