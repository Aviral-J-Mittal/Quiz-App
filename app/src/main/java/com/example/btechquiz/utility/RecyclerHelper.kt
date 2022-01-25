package com.example.btechquiz.utility



import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView


import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.btechquiz.R


class RecyclerHelper(helper: PagerSnapHelper, numberText: TextView, button: Button,bookButton:ImageView) : RecyclerView.OnScrollListener(){
    private val snapHelp = helper
    companion object {
        val quesFrag = arrayOfNulls<ImageButton>(HelperQuestions
                .queList!!.size)
        var recyclerItem:RecyclerView?=null
        var quesId:Int?=null
    }
    private val bookMark=bookButton
    private val numberQues = numberText
    private val clrButton = button

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val view = snapHelp.findSnapView(recyclerView.layoutManager)
        quesId = recyclerView.layoutManager!!.getPosition(view!!)
        numberQues.text = (quesId!! + 1).toString().plus("/").plus(HelperQuestions.queList!!.size.toString())

            if (HelperQuestions.queList!![quesId!!].status == 1) {
                HelperQuestions.queList!![quesId!!].status = 2
            }
            clrButton.setOnClickListener {
                if (quesFrag[quesId!!] != null) {
                    quesFrag[quesId!!]!!.setBackgroundResource(R.drawable.unselected_btn)
                    HelperQuestions.queList!![quesId!!].selectedAns="unselected"
                    HelperQuestions.queList!![quesId!!].status = 2
                    quesFrag[quesId!!] = null
                }



            }

    }




}