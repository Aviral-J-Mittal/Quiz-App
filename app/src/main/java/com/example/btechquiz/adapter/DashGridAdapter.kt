package com.example.btechquiz.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.btechquiz.databinding.GridLayoutBinding
import com.example.btechquiz.model.GridDes
import com.example.btechquiz.navigator.ItemClicked

class DashGridAdapter(private val items:ArrayList<GridDes>,private val listener: ItemClicked) : RecyclerView.Adapter<DashGridAdapter.GridViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {

        return GridViewHolder(GridLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        val currentItem = items[position]
        holder.mGridLayoutBinding.catName.text=currentItem.name
        holder.mGridLayoutBinding.numberTest.text = currentItem.testsNo
        holder.mGridLayoutBinding.gridCard.setOnClickListener {
            listener.onItemClicked(holder.mGridLayoutBinding.catName.text.toString(),position)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


    inner class GridViewHolder(val mGridLayoutBinding: GridLayoutBinding): RecyclerView.ViewHolder(mGridLayoutBinding.root)


}
