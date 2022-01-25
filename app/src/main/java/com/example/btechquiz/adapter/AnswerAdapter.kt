package com.example.btechquiz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.btechquiz.R
import com.example.btechquiz.databinding.AnswerLayoutBinding
import com.example.btechquiz.model.QuestionModel

class AnswerAdapter( private val items: ArrayList<QuestionModel>,private val context: Context) : RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AnswerAdapter.AnswerViewHolder {
        return AnswerViewHolder(AnswerLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: AnswerAdapter.AnswerViewHolder, position: Int) {
        val currentItem = items[position]
        holder.mAnswerLayoutBinding.quesNo.text=(position+1).toString()
        Glide.with(context).load(currentItem.question)
            .into(holder.mAnswerLayoutBinding.question)
        Glide.with(context).load(currentItem.optionA).into(holder.mAnswerLayoutBinding.optionA)
        Glide.with(context).load(currentItem.optionB).into(holder.mAnswerLayoutBinding.optionB)
        Glide.with(context).load(currentItem.optionC).into(holder.mAnswerLayoutBinding.optionC)
        Glide.with(context).load(currentItem.optionD).into(holder.mAnswerLayoutBinding.optionD)
        if(currentItem.selectedAns=="unselected")
        {
            holder.mAnswerLayoutBinding.result.text="Your status : ".plus("Un-Answered")
            holder.mAnswerLayoutBinding.result.setTextColor(ContextCompat.getColor(holder.mAnswerLayoutBinding.root.context, R.color.black))
            holder.checkCorrectAns(currentItem.answer,ContextCompat.getColor(holder.mAnswerLayoutBinding.root.context, R.color.green))
        }
        else{
            if (currentItem.selectedAns==currentItem.answer)
            {
                holder.mAnswerLayoutBinding.result.text="Your status : ".plus("Correct")
                holder.mAnswerLayoutBinding.result.setTextColor(ContextCompat.getColor(holder.mAnswerLayoutBinding.root.context, R.color.green))
                holder.checkCorrectAns(currentItem.answer,ContextCompat.getColor(holder.mAnswerLayoutBinding.root.context, R.color.green))
            }
            else{
                holder.mAnswerLayoutBinding.result.text="Your status : ".plus("Wrong")
                holder.mAnswerLayoutBinding.result.setTextColor(ContextCompat.getColor(holder.mAnswerLayoutBinding.root.context, R.color.red))
                holder.checkCorrectAns(currentItem.answer,ContextCompat.getColor(holder.mAnswerLayoutBinding.root.context, R.color.green))
                holder.checkCorrectAns(currentItem.selectedAns,ContextCompat.getColor(holder.mAnswerLayoutBinding.root.context, R.color.red))

            }
        }

    }

    override fun getItemCount(): Int {
     return items.size
    }
    inner class AnswerViewHolder(val mAnswerLayoutBinding: AnswerLayoutBinding): RecyclerView.ViewHolder(mAnswerLayoutBinding.root){
        fun checkCorrectAns(correctAns:String,color:Int){
            when(correctAns){
                "A)"-> mAnswerLayoutBinding.optionA.setBackgroundColor(color)
                "B)"->mAnswerLayoutBinding.optionB.setBackgroundColor(color)
                "C)"->mAnswerLayoutBinding.optionC.setBackgroundColor(color)
                "D)"->mAnswerLayoutBinding.optionD.setBackgroundColor(color)
            }
        }
    }

}