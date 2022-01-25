package com.example.btechquiz.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.btechquiz.R
import com.example.btechquiz.databinding.QuestionsGridLayoutBinding
import com.example.btechquiz.navigator.GridItemClicked
import com.example.btechquiz.utility.HelperQuestions

class QuestionsGridAdapter(private val items: Int,private val listener: GridItemClicked) : RecyclerView.Adapter<QuestionsGridAdapter.QuestionsGridViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionsGridAdapter.QuestionsGridViewHolder {
        return QuestionsGridViewHolder(
                QuestionsGridLayoutBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: QuestionsGridViewHolder, position: Int) {
        holder.mQuestionsGridLayoutBinding.queNum.text = (position + 1).toString()
        holder.bind(position)
        holder.click(listener,position)

    }

    override fun getItemCount(): Int {
        return items

    }


    inner class QuestionsGridViewHolder(val mQuestionsGridLayoutBinding: QuestionsGridLayoutBinding) : RecyclerView.ViewHolder(mQuestionsGridLayoutBinding.root) {
        fun bind(position: Int) {
            when (HelperQuestions.queList!![position].status) {
                1 -> {
                    mQuestionsGridLayoutBinding.queCard.setCardBackgroundColor(ContextCompat.getColor(mQuestionsGridLayoutBinding.root.context, R.color.darkGrey))
                }
                2 -> {
                    mQuestionsGridLayoutBinding.queCard.setCardBackgroundColor(ContextCompat.getColor(mQuestionsGridLayoutBinding.root.context, R.color.red))
                }
                3 -> {
                    mQuestionsGridLayoutBinding.queCard.setCardBackgroundColor(ContextCompat.getColor(mQuestionsGridLayoutBinding.root.context, R.color.blue))
                }
                4->{
                    mQuestionsGridLayoutBinding.queCard.setCardBackgroundColor(ContextCompat.getColor(mQuestionsGridLayoutBinding.root.context, R.color.green))
                }
            }
        }
        fun click(listener:GridItemClicked,position: Int)
        {
            mQuestionsGridLayoutBinding.queCard.setOnClickListener {
                listener.onClicked(position)
            }

        }

    }
}