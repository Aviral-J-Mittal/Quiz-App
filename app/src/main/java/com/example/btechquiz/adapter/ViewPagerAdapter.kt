package com.example.btechquiz.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.btechquiz.view.DashFragment
import com.example.btechquiz.view.LeaderFragment

class ViewPagerAdapter(fragmentManager: FragmentManager,lifecycle:Lifecycle):FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
      return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0-> DashFragment()
            1-> LeaderFragment()
            else ->{
                DashFragment()
            }
        }
    }
}