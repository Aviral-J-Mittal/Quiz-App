package com.example.btechquiz.view


import android.content.Intent
import android.os.*
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.btechquiz.R
import com.example.btechquiz.databinding.ActivityRegisterBinding
import com.example.btechquiz.utility.*
import com.example.btechquiz.viewmodel.RegisterViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class RegisterActivity : AppCompatActivity() {
    var mActivityRegisterBinding: ActivityRegisterBinding? = null
    private var mRegisterViewModel: RegisterViewModel? = null
    var coming: Boolean = true
    private var exit: Int? = null
    var mProgressDialogMsg: ProgressDialogMsg? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(mActivityRegisterBinding!!.root)
        mRegisterViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(RegisterViewModel::class.java)

        mActivityRegisterBinding!!.viewmodel = mRegisterViewModel
        mRegisterViewModel!!.error1.observe(this, { newError1 ->
            if (newError1 == 1) {
                mActivityRegisterBinding!!.loginLayout0.error = "Username can't be empty"
            } else {
                mActivityRegisterBinding!!.loginLayout0.isErrorEnabled = false
            }

        })
        mRegisterViewModel!!.error2.observe(this, { newError2 ->
            if (newError2 == 1) {
                mActivityRegisterBinding!!.loginLayout1.error = "Enter valid mail id"
            } else {
                mActivityRegisterBinding!!.loginLayout1.isErrorEnabled = false
            }
        })
        mRegisterViewModel!!.passCheck3.observe(this, { newPassCheck3 ->
            if (newPassCheck3 == 2) {
                mActivityRegisterBinding!!.card1.setBackgroundColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.green
                    )
                )
            } else {
                mActivityRegisterBinding!!.card1.setBackgroundColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.grey
                    )
                )
            }
        })
        mRegisterViewModel!!.passCheck1.observe(this, { newPassCheck1 ->
            if (newPassCheck1 == 2) {
                mActivityRegisterBinding!!.card2.setBackgroundColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.green
                    )
                )
            } else {
                mActivityRegisterBinding!!.card2.setBackgroundColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.grey
                    )
                )
            }
        })
        mRegisterViewModel!!.passCheck4.observe(this, { newPassCheck4 ->
            if (newPassCheck4 == 2) {
                mActivityRegisterBinding!!.card3.setBackgroundColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.green
                    )
                )
            } else {
                mActivityRegisterBinding!!.card3.setBackgroundColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.grey
                    )
                )
            }
        })
        mRegisterViewModel!!.passCheck2.observe(this, { newPassCheck2 ->
            if (newPassCheck2 == 2) {
                mActivityRegisterBinding!!.card4.setBackgroundColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.green
                    )
                )
            } else {
                mActivityRegisterBinding!!.card4.setBackgroundColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.grey
                    )
                )
            }
        })
        mRegisterViewModel!!.moveLogin.observe(this, { newMoveLogin ->
            if (newMoveLogin == 2) {
                Toast.makeText(
                    this,
                    "Don't exit while registration in progress",
                    Toast.LENGTH_SHORT
                ).show()
                newUserRegister()
                mRegisterViewModel!!.moveLogin.value = 4

            } else if (newMoveLogin == 1) {

                Toast.makeText(this, "Password conditions not met", Toast.LENGTH_LONG).show()
                mRegisterViewModel!!.moveLogin.value = 3
            }


        })
    }

    private fun newUserRegister() {
        mProgressDialogMsg = ProgressDialogMsg(this)
        mProgressDialogMsg!!.startLoadingDialog("Logging...")
        val auth = FirebaseAuth.getInstance()
        val fireStore = FirebaseFirestore.getInstance()
        auth.createUserWithEmailAndPassword(
            mActivityRegisterBinding!!.loginEdit1.text.toString(),
            mActivityRegisterBinding!!.loginEdit2.text.toString()
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                exit = 2
                val userMap = hashMapOf(
                    "EMAIL_ID" to mActivityRegisterBinding!!.loginEdit1.text.toString(),
                    "NAME" to mActivityRegisterBinding!!.loginEdit0.text.toString(),
                    "PASSWORD" to mActivityRegisterBinding!!.loginEdit2.text.toString(),
                    "TOTAL_SCORE" to 0
                    )
                val userDoc = fireStore.collection("USERS")
                    .document(FirebaseAuth.getInstance().currentUser!!.uid)
                val batch = fireStore.batch()
                batch.set(userDoc, userMap)
                val countDoc = fireStore.collection("USERS").document("TOTAL_USERS")
                batch.update(countDoc, "COUNT", FieldValue.increment(1))
                GlobalScope.launch(Dispatchers.Main)
                {
                    try {
                        batch.commit().await()
                        withContext(Dispatchers.Main) {
                            coming = false
                            startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                            finish()
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main)
                        {
                            mProgressDialogMsg!!.dismissDialog()
                            val toast =
                                Toast.makeText(this@RegisterActivity, e.message, Toast.LENGTH_LONG)
                            toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0)
                            toast.show()
                        }
                    }
                }


            } else {
                mProgressDialogMsg!!.dismissDialog()

                Toast.makeText(this, task.exception!!.message, Toast.LENGTH_LONG).show()
            }

        }
    }


}


