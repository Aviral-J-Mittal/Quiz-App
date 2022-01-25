package com.example.btechquiz.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.btechquiz.databinding.ActivityStartTestBinding
import com.example.btechquiz.utility.HelperQuestions
import com.example.btechquiz.viewmodel.QuestionsViewModel
import com.example.btechquiz.viewmodelfactory.QuestionsViewModelFactory


class StartTestActivity : AppCompatActivity() {
    private var mActivityStartTestBinding: ActivityStartTestBinding? = null
    private var mQuestionsViewModel: QuestionsViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityStartTestBinding = ActivityStartTestBinding.inflate(layoutInflater)
        setContentView(mActivityStartTestBinding!!.root)
        val name = intent.getStringExtra("catName")
        val idCat = intent.getStringExtra("catId")
        val testName = intent.getStringExtra("testName")
        val score = intent.getStringExtra("testScore")
        val time = intent.getStringExtra("testTime")

        mQuestionsViewModel = ViewModelProvider(
            this,
            QuestionsViewModelFactory(idCat!!, testName!!)
        ).get(QuestionsViewModel::class.java)
        HelperQuestions.queList = mQuestionsViewModel!!.questionList
        mQuestionsViewModel!!.handleEvent.observe(this, {
            if (it == 2) {
                mActivityStartTestBinding!!.catId.text = name
                mActivityStartTestBinding!!.testId.text = testName
                mActivityStartTestBinding!!.quesNo.text =
                    mQuestionsViewModel!!.questionList.size.toString()
                mActivityStartTestBinding!!.scoreNo.text = score
                mActivityStartTestBinding!!.timeNo.text = time.plus(" min")
            } else if (it == 3) {
                Toast.makeText(this, "failed to load data", Toast.LENGTH_SHORT).show()

            }
        })

        mActivityStartTestBinding!!.startBtn.setOnClickListener {
            val i = Intent(this, QuestionsActivity::class.java)
            i.putExtra("catName", name)
            i.putExtra("testTime", time)
            startActivity(i)
            finish()

        }
    }


}