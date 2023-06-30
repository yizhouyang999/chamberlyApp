package com.example.chamberlyapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegistrationActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore

    private lateinit var displayName: EditText
    private lateinit var btnContinue: Button
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        db = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()

        displayName = findViewById(R.id.display_name)
        btnContinue = findViewById(R.id.continue_button)


        btnContinue.setOnClickListener {
            val sDisplayName = displayName.text.toString().trim()

            // Query the collection to check if the display_name already exists
            db.collection("Display_Names")
                .whereEqualTo("Display_Name", sDisplayName)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        if (task.result?.isEmpty == true) {
                            // display_name is unique, proceed with adding it to the database


                            firebaseAuth.signInAnonymously()
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        // User signed in anonymously
                                        val user = firebaseAuth.currentUser
                                        if (user != null && user.isAnonymous) {
                                            // Anonymous user created
                                            val userId = user.uid
                                            // Perform any necessary operations with the anonymous user
                                        }
                                    } else {
                                        // Handle error
                                        val exception = task.exception
                                        if (exception != null) {
                                            // Handle exception
                                        }
                                    }
                                }
                            val sUID = firebaseAuth.currentUser!!.uid.toString()
                            val sEmail = sUID + "@chamberly.net"

                            val userMap = hashMapOf(
                                "Display_Name" to sDisplayName,
                                "Email" to sEmail,
                                "UID" to sUID
                            )
                            db.collection("Display_Names")
                                .document(sDisplayName)
                                .set(userMap)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Successfully added", Toast.LENGTH_SHORT)
                                        .show()
                                    displayName.text.clear()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                                }


                        } else {
                            // display_name already exists, display an error message
                            Toast.makeText(this, "Display name already exists", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        // Failed to query the collection
                        Toast.makeText(this, "Failed to check display name", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
    }
}