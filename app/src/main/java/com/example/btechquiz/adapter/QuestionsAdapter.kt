package com.example.btechquiz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.btechquiz.databinding.QuestionsItemBinding
import com.example.btechquiz.model.QuestionModel
import com.example.btechquiz.navigator.ItemQuestionsClicked
import com.example.btechquiz.utility.RecyclerHelper


class QuestionsAdapter(
    private val items: ArrayList<QuestionModel>,
    private val context: Context,
    private val listener: ItemQuestionsClicked
) :
    RecyclerView.Adapter<QuestionsAdapter.QuestionsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionsViewHolder {
        return QuestionsViewHolder(
            QuestionsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: QuestionsViewHolder, position: Int) {
        val currentItem = items[position]
        Glide.with(context).load(currentItem.question)
            .into(holder.mQuestionsItemBinding.questionImage)
        Glide.with(context).load(currentItem.optionA).into(holder.mQuestionsItemBinding.optionA)
        Glide.with(context).load(currentItem.optionB).into(holder.mQuestionsItemBinding.optionB)
        Glide.with(context).load(currentItem.optionC).into(holder.mQuestionsItemBinding.optionC)
        Glide.with(context).load(currentItem.optionD).into(holder.mQuestionsItemBinding.optionD)
        holder.bind(listener, position)

    }

    override fun getItemCount(): Int {
        return items.size
    }




    inner class QuestionsViewHolder(val mQuestionsItemBinding: QuestionsItemBinding) :
        RecyclerView.ViewHolder(mQuestionsItemBinding.root) {
        fun bind(clickListener: ItemQuestionsClicked, quesNo: Int) {
            mQuestionsItemBinding.optionA.setOnClickListener {
                RecyclerHelper.quesFrag[quesNo]=mQuestionsItemBinding.optionA

                clickListener.onClicked(
                    mQuestionsItemBinding.optionA,
                    mQuestionsItemBinding.optionB,
                    mQuestionsItemBinding.optionC,
                    mQuestionsItemBinding.optionD,
                    "A)",
                    quesNo
                )
            }
            mQuestionsItemBinding.optionB.setOnClickListener {
                RecyclerHelper.quesFrag[quesNo]=mQuestionsItemBinding.optionB
                clickListener.onClicked(
                    mQuestionsItemBinding.optionB,
                    mQuestionsItemBinding.optionC,
                    mQuestionsItemBinding.optionD,
                    mQuestionsItemBinding.optionA,
                    "B)",
                    quesNo
                )
            }
            mQuestionsItemBinding.optionC.setOnClickListener {
                RecyclerHelper.quesFrag[quesNo]=mQuestionsItemBinding.optionC
                clickListener.onClicked(
                    mQuestionsItemBinding.optionC,
                    mQuestionsItemBinding.optionD,
                    mQuestionsItemBinding.optionA,
                    mQuestionsItemBinding.optionB,
                    "C)",
                    quesNo
                )
            }
            mQuestionsItemBinding.optionD.setOnClickListener {
                RecyclerHelper.quesFrag[quesNo]=mQuestionsItemBinding.optionD
                clickListener.onClicked(
                    mQuestionsItemBinding.optionD,
                    mQuestionsItemBinding.optionA,
                    mQuestionsItemBinding.optionB,
                    mQuestionsItemBinding.optionC,
                    "D)",
                    quesNo
                )
            }


        }
    }
}