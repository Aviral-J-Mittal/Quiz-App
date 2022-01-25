package com.example.btechquiz.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.btechquiz.adapter.ProgressAdapter
import com.example.btechquiz.databinding.ActivityProgressBinding
import com.example.btechquiz.navigator.ItemProgressClicked
import com.example.btechquiz.utility.HelperQuestions
import com.example.btechquiz.viewmodel.ProgressViewModel
import com.example.btechquiz.viewmodelfactory.ProgressViewModelFactory

class ProgressActivity : AppCompatActivity(), ItemProgressClicked {
    var mProgressViewModel: ProgressViewModel? = null
    var check = true
    var name: String? = null
    var adapter:ProgressAdapter?=null
    var pos: String? = null
    private var mActivityProgressBinding: ActivityProgressBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityProgressBinding = ActivityProgressBinding.inflate(layoutInflater)
        setContentView(mActivityProgressBinding!!.root)
        mActivityProgressBinding!!.progress.visibility = View.VISIBLE
        val intent = intent
        name = intent.getStringExtra("DashName")
        pos = intent.getStringExtra("DashId")
        HelperQuestions.selectedCategoryID=pos
        val testsNo = intent.getStringExtra("DashTests")
        mProgressViewModel =
            ViewModelProvider(this, ProgressViewModelFactory(pos!!, testsNo!!)).get(
                ProgressViewModel::class.java
            )

        supportActionBar!!.title = name
        mProgressViewModel!!.show.observe(this, { newShow ->
            if (newShow == 2) {
                mActivityProgressBinding!!.progress.visibility = View.INVISIBLE
                val linear = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                mActivityProgressBinding!!.progressRecycler.layoutManager = linear
                 adapter = ProgressAdapter(mProgressViewModel!!.data, this)

                mActivityProgressBinding!!.progressRecycler.adapter = adapter

            } else if (newShow == 3) {
                mActivityProgressBinding!!.progress.visibility = View.INVISIBLE
                Toast.makeText(this, "Oops! Failed to load data", Toast.LENGTH_SHORT).show()

            }
        })
    }

    override fun onItemClicked(testPos: Int) {
        check = false
        HelperQuestions.selectedTest=testPos
        val intent = Intent(this, StartTestActivity::class.java)
        intent.putExtra("catName", name)
        intent.putExtra("catId", pos)
        intent.putExtra("testName",mProgressViewModel!!.data[testPos].type)
        intent.putExtra("testScore",mProgressViewModel!!.data[testPos].score.toString())
        intent.putExtra("testTime",mProgressViewModel!!.data[testPos].time.toString())
        startActivity(intent)
        finish()
    }

}
