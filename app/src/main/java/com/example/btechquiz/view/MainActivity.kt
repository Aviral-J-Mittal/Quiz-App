package com.example.btechquiz.view


import android.content.Intent
import android.net.Uri


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat

import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide

import com.example.btechquiz.R
import com.example.btechquiz.adapter.ViewPagerAdapter

import com.example.btechquiz.databinding.ActivityMainBinding

import com.example.btechquiz.utility.HelperQuestions


import com.example.btechquiz.viewmodel.MainViewModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var mActivityMainBinding: ActivityMainBinding? = null
    private var mMainViewModel: MainViewModel? = null
    private var coming: Boolean = true
    private var uri: Uri?=null
    private lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mActivityMainBinding!!.root)
        setSupportActionBar(mActivityMainBinding!!.barTool)
        mMainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(MainViewModel::class.java)
        mMainViewModel!!.image.observe(this,{
            uri=it
            Glide.with(this).load(it).into(mActivityMainBinding!!.navView.getHeaderView(0).findViewById(R.id.roundedMainImage))
        })
        mActivityMainBinding!!.navView.setNavigationItemSelectedListener(this)
        toggle = ActionBarDrawerToggle(
            this,
            mActivityMainBinding!!.drawerLayout,
            mActivityMainBinding!!.barTool,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        mActivityMainBinding!!.navView.getHeaderView(0)
            .findViewById<ImageView>(R.id.roundedMainImage).setOnClickListener {
                val intent=Intent(this, ProfileActivity::class.java)
                intent.putExtra(
                    "NAME", mActivityMainBinding!!.navView.getHeaderView(0)
                        .findViewById<TextView>(R.id.name).text
                )
                intent.putExtra("EMAIL", mActivityMainBinding!!.navView.getHeaderView(0)
                    .findViewById<TextView>(R.id.mail).text )
                intent.putExtra("IMAGE",uri.toString())

                startActivity(intent)

            }
        FirebaseFirestore.getInstance().collection("USERS")
            .document(FirebaseAuth.getInstance().currentUser!!.uid).get().addOnSuccessListener {
                HelperQuestions.userName=it.getString("NAME")
                mActivityMainBinding!!.navView.getHeaderView(0)
                    .findViewById<TextView>(R.id.name).text =
                    HelperQuestions.userName
                mActivityMainBinding!!.navView.getHeaderView(0)
                    .findViewById<TextView>(R.id.mail).text =
                    it.getString("EMAIL_ID")




            }

        mActivityMainBinding!!.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        mActivityMainBinding!!.bottomNavBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.dash -> {

                    mActivityMainBinding!!.frame.currentItem=0
                }
                R.id.leader -> {
                    mActivityMainBinding!!.frame.currentItem=1

                }
                R.id.discuss->{
                    Toast.makeText(this,"Coming Soon!!", Toast.LENGTH_SHORT).show()
                }

            }
            true
        }
        mActivityMainBinding!!.frame.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                 if(position==0)
                 {
                     mActivityMainBinding!!.bottomNavBar.selectedItemId=R.id.dash
                 }
                else
                 {
                     mActivityMainBinding!!.bottomNavBar.selectedItemId=R.id.leader
                 }

            }
        })

        val adapter=ViewPagerAdapter(supportFragmentManager,lifecycle)
        mActivityMainBinding!!.frame.adapter=adapter



    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.log_out -> {
                FirebaseAuth.getInstance().signOut()

                mMainViewModel!!.logOutIntpo()
                mMainViewModel!!.currentInte.observe(this,  { newCurrentInte ->
                    if (newCurrentInte == 2 && coming) {
                        coming = false

                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                        mMainViewModel!!.currentInte.value = 3
                    }
                })


            }
            R.id.saved->{
                startActivity(Intent(this,BookMarkActivity::class.java))
            }
            R.id.contest->{
                Toast.makeText(this,"Coming Soon!!", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }

    override fun onBackPressed() {
        if (mActivityMainBinding!!.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            mActivityMainBinding!!.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


}