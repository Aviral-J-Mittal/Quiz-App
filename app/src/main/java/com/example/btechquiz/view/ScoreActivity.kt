package com.example.btechquiz.view


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.btechquiz.databinding.ActivityScoreBinding
import com.example.btechquiz.utility.HelperQuestions
import com.example.btechquiz.utility.ProgressDialogMsg
import com.example.btechquiz.viewmodel.ProgressViewModel
import com.example.btechquiz.viewmodel.ScoreViewModel
import com.example.btechquiz.viewmodelfactory.ProgressViewModelFactory
import com.example.btechquiz.viewmodelfactory.ScoreViewModelFactory
import java.util.concurrent.TimeUnit

class ScoreActivity : AppCompatActivity() {
    var mActivityScoreBinding:ActivityScoreBinding?=null
    var mScoreViewModel: ScoreViewModel?=null
    private var mProgressDialogMsg:ProgressDialogMsg?=null
    private var finalScore:Int?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityScoreBinding =ActivityScoreBinding.inflate(layoutInflater)
        setContentView(mActivityScoreBinding!!.root)
        supportActionBar!!.title="RESULT"
        mProgressDialogMsg = ProgressDialogMsg(this)
        mProgressDialogMsg!!.startLoadingDialog("Computing Result...")
        loadData()
        mActivityScoreBinding!!.answers.setOnClickListener {
            startActivity(Intent(this,AnswerActivity::class.java))
        }
        mScoreViewModel!!.result!!.observe(this, Observer {
            if(it=="successful")
            {
                mProgressDialogMsg!!.dismissDialog()

            }
            else
            {
                mProgressDialogMsg!!.dismissDialog()
                Toast.makeText(this,"Something went wrong!",Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun loadData()
    {
        var correct=0
        var inCorrect=0
        var unAttempted=0
        for(i in 0 until HelperQuestions.queList!!.size)
        {
            if (HelperQuestions.queList!![i].selectedAns=="unselected")
            {
                unAttempted++
            }
            else
            {
                if (HelperQuestions.queList!![i].selectedAns==HelperQuestions.queList!![i].answer)
                {
                    correct++
                }
                else
                {
                    inCorrect++
                }
            }
        }
         finalScore=(correct*100)/HelperQuestions.queList!!.size
        mActivityScoreBinding!!.rightAns.text=correct.toString()
        mActivityScoreBinding!!.incorrectAns.text=inCorrect.toString()
        mActivityScoreBinding!!.unAttemptedAns.text=unAttempted.toString()
        mActivityScoreBinding!!.scoreId.text= HelperQuestions.queList!!.size.toString()
        mActivityScoreBinding!!.finalScore.text=finalScore.toString().plus("%")
        val time=intent.getLongExtra("TIME_TAKEN",0)
        val timeTaken= TimeUnit.MILLISECONDS.toMinutes(time)
            .toString() + ":" + (TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(
            TimeUnit.MILLISECONDS.toMinutes(time)
        )).toString()
        mActivityScoreBinding!!.scoreTime.text=timeTaken.plus(" min")
        mScoreViewModel= ViewModelProvider(this, ScoreViewModelFactory(finalScore!!)).get(
                ScoreViewModel::class.java)

    }


}