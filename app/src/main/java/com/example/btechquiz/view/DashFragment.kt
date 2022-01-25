package com.example.btechquiz.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.btechquiz.R
import com.example.btechquiz.adapter.DashGridAdapter
import com.example.btechquiz.databinding.FragmentDashBinding
import com.example.btechquiz.databinding.GridLayoutBinding
import com.example.btechquiz.navigator.ItemClicked
import com.example.btechquiz.viewmodel.DashFragmentViewModel


class DashFragment : Fragment(), ItemClicked {
    var mDashBinding: FragmentDashBinding? = null
    private var mDashFragmentViewModel: DashFragmentViewModel? = null
    private var grid: GridLayoutManager? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mDashBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_dash,
            container,
            false
        )
        mDashBinding!!.progress.visibility=View.VISIBLE

        mDashFragmentViewModel = ViewModelProvider(
            this
        ).get(DashFragmentViewModel::class.java)


        mDashFragmentViewModel!!.action.observe(viewLifecycleOwner,  { newAction->
            if(newAction==2)
            {
                mDashBinding!!.progress.visibility=View.INVISIBLE
                grid = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
                mDashBinding!!.recycler.layoutManager = grid
                val adapter = DashGridAdapter(mDashFragmentViewModel!!.categoryList, this)

                mDashBinding!!.recycler.adapter = adapter

            }
            else if(newAction==3)
            {
                mDashBinding!!.progress.visibility=View.INVISIBLE
                Toast.makeText(activity,"Oops! Failed to load data",Toast.LENGTH_SHORT).show()

            }

        })


        return mDashBinding!!.root

    }

    override fun onItemClicked(catName:String,catPos:Int) {
        val intent = Intent(activity, ProgressActivity::class.java)
        intent.putExtra("DashName",catName)
        intent.putExtra("DashId", mDashFragmentViewModel!!.categoryList[catPos].ID)
        intent.putExtra("DashTests",mDashFragmentViewModel!!.categoryList[catPos].testsNo)
        startActivity(intent)

    }


}