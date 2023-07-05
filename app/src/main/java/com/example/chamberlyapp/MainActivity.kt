package com.example.chamberlyapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore

    private lateinit var btnCreate: Button
    private lateinit var imgInsta: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCreate = findViewById(R.id.create_button)
        imgInsta = findViewById(R.id.clickable_image)

        btnCreate.setOnClickListener{
            val intent = Intent(this, StartchatActivity::class.java)
            startActivity(intent)
        }

        imgInsta.setOnClickListener {
            val url = "https://google.com" // Replace with your desired URL
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }


    }


}