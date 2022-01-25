package com.example.btechquiz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.btechquiz.databinding.BookmarkitemBinding
import com.example.btechquiz.model.QuestionModel


class BookMarkAdapter(private val items:ArrayList<QuestionModel>,private val context: Context):RecyclerView.Adapter<BookMarkAdapter.BookMarkViewHolder>() {

     fun delete(i:Int){
         items.removeAt(i)
         notifyDataSetChanged()
     }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookMarkAdapter.BookMarkViewHolder {
        return BookMarkViewHolder(
            BookmarkitemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BookMarkAdapter.BookMarkViewHolder, position: Int) {
        val currentItem = items[position]
        holder.mBookmarkitemBinding.quesNo.text = (position + 1).toString()
        Glide.with(context).load(currentItem.question)
            .into(holder.mBookmarkitemBinding.question)
        Glide.with(context).load(currentItem.optionA).into(holder.mBookmarkitemBinding.optionA)
        Glide.with(context).load(currentItem.optionB).into(holder.mBookmarkitemBinding.optionB)
        Glide.with(context).load(currentItem.optionC).into(holder.mBookmarkitemBinding.optionC)
        Glide.with(context).load(currentItem.optionD).into(holder.mBookmarkitemBinding.optionD)
        Glide.with(context).load(currentItem.optionD).into(holder.mBookmarkitemBinding.optionD)
        holder.mBookmarkitemBinding.answer.text = "Answer : ".plus(currentItem.answer)


    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class BookMarkViewHolder(val mBookmarkitemBinding: BookmarkitemBinding) :
        RecyclerView.ViewHolder(mBookmarkitemBinding.root)


}
