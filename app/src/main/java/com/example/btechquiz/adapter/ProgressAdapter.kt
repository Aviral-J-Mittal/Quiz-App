package com.example.btechquiz.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.btechquiz.databinding.ProgressRecyclerBinding
import com.example.btechquiz.model.ProgressModel
import com.example.btechquiz.navigator.ItemProgressClicked

class ProgressAdapter(private val items:ArrayList<ProgressModel>,private val listener: ItemProgressClicked) : RecyclerView.Adapter<ProgressAdapter.RecyclerViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {

        return RecyclerViewHolder(ProgressRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val currentItem = items[position]
        holder.mHomeItemBinding.progressTitle.text=currentItem.type
        holder.mHomeItemBinding.progressBar.progress=currentItem.score
        holder.mHomeItemBinding.scoreText.text=currentItem.score.toString().plus(" %")
        holder.mHomeItemBinding.progressLayout.setOnClickListener {
            listener.onItemClicked(position)
        }
    }
    override fun getItemCount(): Int {
        return items.size
    }





    inner class RecyclerViewHolder(val mHomeItemBinding:ProgressRecyclerBinding): RecyclerView.ViewHolder(mHomeItemBinding.root)


}