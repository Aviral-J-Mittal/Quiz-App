package com.example.btechquiz.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.btechquiz.R
import com.example.btechquiz.databinding.ActivityLoginBinding
import com.example.btechquiz.utility.ProgressDialogMsg
import com.example.btechquiz.viewmodel.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

class LoginActivity : AppCompatActivity() {
    var mLoginViewModel: LoginViewModel? = null
    private var mActivityLoginBinding: ActivityLoginBinding? = null
    var mProgressDialogMsg: ProgressDialogMsg? = null
    var coming: Boolean = true
    private var auth: FirebaseAuth? = null
    var googleComing: Boolean = true
    var googleSignInClient: GoogleSignInClient? = null
    var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(mActivityLoginBinding!!.root)
        mLoginViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(LoginViewModel::class.java)
        mActivityLoginBinding!!.viewmodel = mLoginViewModel

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_google_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        mLoginViewModel!!.goTo.observe(this,  { newGoto ->
            if (newGoto == 1) {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
                mLoginViewModel!!.goTo.value = 2
            }
        })
        mLoginViewModel!!.moveTo.observe(this, { newMoveTo ->
            if (newMoveTo == 1) {
                login()
                mLoginViewModel!!.moveTo.value = 2
            } else if (newMoveTo == 3) {
                Toast.makeText(this, "Either of the fields cannot be empty", Toast.LENGTH_SHORT)
                    .show()
                mLoginViewModel!!.moveTo.value = 2
            }
        })
        mLoginViewModel!!.googleSign.observe(this,{ newGoogleSign ->
            if (newGoogleSign == 1) {
                Toast.makeText(this, "Don't exit app while logging", Toast.LENGTH_LONG).show()
                googleComing = false
                signIn()
                mLoginViewModel!!.googleSign.value = 2
            }

        })


    }


    private fun login() {
        mProgressDialogMsg = ProgressDialogMsg(this)
        mProgressDialogMsg!!.startLoadingDialog("Logging...")
        auth = FirebaseAuth.getInstance()
        auth!!.signInWithEmailAndPassword(
            mActivityLoginBinding!!.loginEdit1.text.toString(),
            mActivityLoginBinding!!.loginEdit2.text.toString()
        )
            .addOnCompleteListener { task ->
                mProgressDialogMsg!!.dismissDialog()
                if (task.isSuccessful) {
                    coming = false
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()

                } else {
                    Toast.makeText(this, task.exception!!.message, Toast.LENGTH_LONG).show()
                }
            }


    }


    private val RC_SIGN_IN = 65
    private fun signIn() {
        mProgressDialogMsg = ProgressDialogMsg(this@LoginActivity)
        mProgressDialogMsg!!.startLoadingDialog("Logging...")
        val signInIntent = googleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mProgressDialogMsg!!.dismissDialog()
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {

            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        mProgressDialogMsg = ProgressDialogMsg(this)
        mProgressDialogMsg!!.startLoadingDialog("Logging...")
        mAuth = FirebaseAuth.getInstance()
        val fireStore = FirebaseFirestore.getInstance()
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                mProgressDialogMsg!!.dismissDialog()
                if (task.isSuccessful) {
                    val user = mAuth!!.currentUser
                    if (task.result.additionalUserInfo!!.isNewUser) {
                        val userMap = hashMapOf(
                            "EMAIL_ID" to user!!.email,
                            "NAME" to user.displayName,
                            "PASSWORD" to "",
                            "TOTAL_SCORE" to 0
                        )
                        val userDoc = fireStore.collection("USERS")
                            .document(FirebaseAuth.getInstance().currentUser!!.uid)
                        val batch = fireStore.batch()
                        batch.set(userDoc, userMap)
                        val countDoc = fireStore.collection("USERS").document("TOTAL_USERS")
                        batch.update(countDoc, "COUNT", FieldValue.increment(1))
                        GlobalScope.launch(Dispatchers.IO) {
                            try {
                                batch.commit().await()
                                withContext(Dispatchers.Main)
                                {
                                    googleComing = false
                                    startActivity(
                                        Intent(
                                            this@LoginActivity,
                                            MainActivity::class.java
                                        )
                                    )
                                    finish()
                                }
                            } catch (e: Exception) {
                                val toast =
                                    Toast.makeText(
                                        this@LoginActivity,
                                        e.message,
                                        Toast.LENGTH_LONG
                                    )
                                toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0)
                                toast.show()
                            }
                        }

                    } else {
                        googleComing = false
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }


                } else {
                    Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show()
                }
            }
    }


}