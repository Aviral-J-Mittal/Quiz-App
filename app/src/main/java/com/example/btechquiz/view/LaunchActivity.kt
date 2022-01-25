package com.example.btechquiz.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.btechquiz.R
import com.example.btechquiz.databinding.ActivityLaunchBinding
import com.example.btechquiz.viewmodel.LaunchViewModel
import com.google.firebase.auth.FirebaseAuth

class LaunchActivity : AppCompatActivity(){
    var mActivityLaunchBinding: ActivityLaunchBinding? = null
    var mLaunchViewModel: LaunchViewModel? = null
    var topAnim: Animation? = null
    private var auth: FirebaseAuth= FirebaseAuth.getInstance()
    var coming:Boolean=true
    var enlargeAnim: Animation? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        mActivityLaunchBinding = ActivityLaunchBinding.inflate(layoutInflater)
        setContentView(mActivityLaunchBinding!!.root)
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        enlargeAnim = AnimationUtils.loadAnimation(this, R.anim.enlarge)
        mLaunchViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(LaunchViewModel::class.java)
        mLaunchViewModel!!.navigateToDetails.observe(this, Observer {

            mActivityLaunchBinding!!.launchImage.animation = topAnim
            mActivityLaunchBinding!!.launchText.animation = enlargeAnim



        })
        mLaunchViewModel!!.splash()
        mLaunchViewModel!!.pos.observe(this, Observer { newPos ->
            if (newPos == 1 && coming) {

                if(auth.currentUser!=null) {
                    coming = false
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    mLaunchViewModel!!.pos.value=2

                }
                else{
                    coming=false
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                    mLaunchViewModel!!.pos.value=2
                }


            }

        })

    }




}
