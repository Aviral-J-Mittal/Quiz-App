package com.example.btechquiz.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.btechquiz.R
import com.example.btechquiz.adapter.LeaderAdapter
import com.example.btechquiz.databinding.FragmentLeaderBinding
import com.example.btechquiz.utility.HelperQuestions
import com.example.btechquiz.viewmodel.DashFragmentViewModel
import com.example.btechquiz.viewmodel.LeaderViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage


class LeaderFragment : Fragment() {
    private var mLeaderViewModel:LeaderViewModel?=null
    private var mFragmentLeaderBinding:FragmentLeaderBinding?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mFragmentLeaderBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_leader,
            container,
            false
        )
        mFragmentLeaderBinding!!.progress.visibility=View.VISIBLE
        FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().uid!!).get().addOnSuccessListener {
            mFragmentLeaderBinding!!.score.text="TOTAL SCORE : ".plus(it.getLong("TOTAL_SCORE")!!.toInt().toString())
        }
        val ref= FirebaseStorage.getInstance().reference.child("images").child(FirebaseAuth.getInstance().uid!!)
        ref.downloadUrl.addOnSuccessListener {
            Glide.with(requireActivity()).load(it).into(mFragmentLeaderBinding!!.rankPersonalImage)
        }
         loadLeaderBoard()
        mFragmentLeaderBinding!!.user.text=HelperQuestions.userName
        return mFragmentLeaderBinding!!.root
    }
    private fun loadLeaderBoard(){
        mLeaderViewModel= ViewModelProvider(
            this
        ).get(LeaderViewModel::class.java)

        mLeaderViewModel!!.result?.observe(viewLifecycleOwner, {

            if(it=="successful"){
                val layoutManager=LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
                val adapter=LeaderAdapter(mLeaderViewModel!!.data,requireActivity())
                mFragmentLeaderBinding!!.leaderRecycler.layoutManager=layoutManager
                mFragmentLeaderBinding!!.leaderRecycler.adapter=adapter
                adapter.notifyDataSetChanged()
                FirebaseFirestore.getInstance().collection("USERS").document("TOTAL_USERS").get().addOnSuccessListener { snapshot->
                    val userCount=snapshot.getLong("COUNT")!!.toInt()
                    mFragmentLeaderBinding!!.userCount.text="Active Users : ".plus(userCount.toString())
                mFragmentLeaderBinding!!.progress.visibility=View.INVISIBLE
            }}
            else{
                Toast.makeText(activity,"Error occurred. Try again!",Toast.LENGTH_SHORT).show()
            }
        })
        mLeaderViewModel!!.userRank.observe(viewLifecycleOwner, {
            mFragmentLeaderBinding!!.rank.text=it
        })

    }

}