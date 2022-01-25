package com.example.btechquiz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.btechquiz.R
import com.example.btechquiz.databinding.RankLayoutBinding
import com.example.btechquiz.model.RankModel


class LeaderAdapter(private val items:ArrayList<RankModel>,private val context: Context): RecyclerView.Adapter<LeaderAdapter.LeaderViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LeaderAdapter.LeaderViewHolder {
        return LeaderViewHolder(RankLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: LeaderAdapter.LeaderViewHolder, position: Int) {
        val currentItem = items[position]
        holder.mRankLayoutBinding.user.text=currentItem.name
        Glide.with(context).load(currentItem.imgProfile).into(holder.mRankLayoutBinding.rankImage)
        holder.mRankLayoutBinding.score.text="TOTAL SCORE : ".plus(currentItem.score.toString())
        holder.mRankLayoutBinding.rank.text=currentItem.rank.toString()
        if(position==0)
        {
            holder.mRankLayoutBinding.linear2.setBackgroundColor(ContextCompat.getColor(holder.mRankLayoutBinding.root.context, R.color.golden))
        }
        if(position==1)
        {
            holder.mRankLayoutBinding.linear2.setBackgroundColor(ContextCompat.getColor(holder.mRankLayoutBinding.root.context, R.color.grey))
        }
        if(position==2)
        {
            holder.mRankLayoutBinding.linear2.setBackgroundColor(ContextCompat.getColor(holder.mRankLayoutBinding.root.context, R.color.bronze))
        }
    }

    override fun getItemCount(): Int {
        return if(items.size>10){
            10
        } else{
            items.size
        }
    }
    inner class LeaderViewHolder(val mRankLayoutBinding: RankLayoutBinding) :RecyclerView.ViewHolder(mRankLayoutBinding.root)

}