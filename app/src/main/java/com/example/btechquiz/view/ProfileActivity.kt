package com.example.btechquiz.view

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.btechquiz.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class ProfileActivity : AppCompatActivity() {
    private var mActivityProfileBinding: ActivityProfileBinding? = null
    private var imageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityProfileBinding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(mActivityProfileBinding!!.root)
        supportActionBar!!.title = "My Profile"
        val name = intent.getStringExtra("NAME")
        val mail = intent.getStringExtra("EMAIL")
        val uriString=intent.getStringExtra("IMAGE")
        mActivityProfileBinding!!.nameEdit.hint = name
        mActivityProfileBinding!!.emailText.text = mail
        val myUri = Uri.parse(uriString)

        Glide.with(this).load(myUri).into(mActivityProfileBinding!!.roundedImage)

        val getAction = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) {
            imageUri = it
            mActivityProfileBinding!!.roundedImage.setImageURI(imageUri)
        }
        mActivityProfileBinding!!.imgBtn.setOnClickListener {
            getAction.launch("image/*")
        }
        mActivityProfileBinding!!.saveBtn.setOnClickListener {
            val storage = FirebaseStorage.getInstance().reference.child("images")
                .child(FirebaseAuth.getInstance().uid!!)
            storage.putFile(imageUri!!).addOnSuccessListener {
                Toast.makeText(this, "Profile photo updated", Toast.LENGTH_SHORT).show()
            }


        }

        mActivityProfileBinding!!.saveName.setOnClickListener {
            val editName = mActivityProfileBinding!!.nameEdit.text.toString()
            FirebaseFirestore.getInstance().collection("USERS")
                .document(FirebaseAuth.getInstance().uid!!).update("NAME", editName).addOnSuccessListener {
                    Toast.makeText(this, "Profile name updated", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Something went wrong!! Try again.", Toast.LENGTH_SHORT).show()
                }
        }

    }
}
