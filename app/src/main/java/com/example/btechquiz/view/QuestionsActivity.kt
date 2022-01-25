package com.example.btechquiz.view


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.btechquiz.R
import com.example.btechquiz.adapter.QuestionsGridAdapter
import com.example.btechquiz.databinding.QuestionsLayoutListBinding
import com.example.btechquiz.navigator.GridItemClicked
import com.example.btechquiz.utility.HelperQuestions
import com.example.btechquiz.utility.RecyclerHelper
import com.example.btechquiz.viewmodel.TimerViewModel
import com.example.btechquiz.viewmodelfactory.TimerViewModelFactory
import com.google.android.material.snackbar.Snackbar


class QuestionsActivity : AppCompatActivity(), DrawerLayout.DrawerListener, GridItemClicked {
    private var mQuestionsLayoutListBinding: QuestionsLayoutListBinding? = null
    private var mTimerViewModel: TimerViewModel? = null
    private var adapter: QuestionsGridAdapter? = null
    private var bundle: Bundle? = null
    private var timeLeft:Long?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mQuestionsLayoutListBinding = QuestionsLayoutListBinding.inflate(layoutInflater)
        setContentView(mQuestionsLayoutListBinding!!.root)
        val name = intent.getStringExtra("catName")
        val time = intent.getStringExtra("testTime")!!.toInt()
        bundle = Bundle()
        bundle!!.putInt("testTime", time)
        mQuestionsLayoutListBinding!!.includeDrawer.category.text = name
        if (savedInstanceState == null) {
            setFragment(QuestionsFragment())
        } else {
            val existence = supportFragmentManager.findFragmentByTag("fragExistence")
            setFragment(existence!!)
        }
        mTimerViewModel =
            ViewModelProvider(this, TimerViewModelFactory(time)).get(
                TimerViewModel::class.java
            )
        mTimerViewModel!!.currentTime.observe(this, Observer {
            mQuestionsLayoutListBinding!!.includeDrawer.countTime.text = it
        })
        mTimerViewModel!!.timeLeft.observe(this,Observer{
            timeLeft=it
            bundle!!.putLong("remainingTime",timeLeft!!)
        })
        mTimerViewModel!!.finish.observe(this, Observer {
            if (it) {
                Toast.makeText(this, "TIME OVER", Toast.LENGTH_SHORT).show()
                startActivity(
                    Intent(this, ScoreActivity::class.java).putExtra(
                        "TIME_TAKEN",
                        (time * 60 * 1000) - timeLeft!!
                    )
                )
                finish()
            }
        })

        mQuestionsLayoutListBinding!!.includeDrawer.quesReview.setOnClickListener {
            if (!mQuestionsLayoutListBinding!!.activityDrawer.isDrawerOpen(GravityCompat.END)) {
                mQuestionsLayoutListBinding!!.activityDrawer.openDrawer(GravityCompat.END)
            }
        }

        val grid = GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false)
        mQuestionsLayoutListBinding?.reviewRecycler?.layoutManager = grid
        adapter = QuestionsGridAdapter(HelperQuestions.queList!!.size, this)
        mQuestionsLayoutListBinding!!.reviewRecycler.adapter = adapter
        mQuestionsLayoutListBinding!!.activityDrawer.addDrawerListener(this)
        mQuestionsLayoutListBinding!!.includeDrawer.markReview.setOnClickListener {
            if (HelperQuestions.queList!![RecyclerHelper.quesId!!].status == 4) {
                HelperQuestions.queList!![RecyclerHelper.quesId!!].status = 3
                Toast.makeText(this, "Review marked", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Only answered questions can be reviewed", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

    private fun setFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        fragment.arguments = bundle
        transaction.replace(
            mQuestionsLayoutListBinding!!.includeDrawer.frame.id,
            fragment,
            "fragExistence"
        )
        transaction.commit()
    }

    override fun onBackPressed() {
        if (mQuestionsLayoutListBinding!!.activityDrawer.isDrawerOpen(GravityCompat.END)) {
            mQuestionsLayoutListBinding!!.activityDrawer.closeDrawer(GravityCompat.END)
        } else {
            super.onBackPressed()
        }
    }

    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

    }


    override fun onDrawerOpened(drawerView: View) {

    }

    override fun onDrawerClosed(drawerView: View) {

    }

    override fun onDrawerStateChanged(newState: Int) {
        adapter!!.notifyDataSetChanged()
    }

    override fun onClicked(itemPos: Int) {
        RecyclerHelper.recyclerItem!!.scrollToPosition(itemPos)
        if (RecyclerHelper.recyclerItem!!.scrollState == RecyclerView.SCROLL_STATE_IDLE) {
            mQuestionsLayoutListBinding!!.activityDrawer.closeDrawer(GravityCompat.END)
        }
    }


}
