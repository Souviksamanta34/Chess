package com.example.chessgame

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

private const val TAG = "MainActivity"


class MainActivity : AppCompatActivity() {
    var dialog = ChoosePiece()

    override fun onCreate(savedInstanceState: Bundle?) {
        //var os = ChessGame2.ChessGame.dialog.show(supportFragmentManager,"")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var firestore = FirebaseFirestore.getInstance()

//        val users: MutableMap<String, Any> = HashMap()
//        users["firstname"] = "EASY"
//        users["lastname"] = "TOTO"
//        users["description"] = "Subscribe"
//
//        firestore.collection("users").add(users)
//            .addOnSuccessListener(OnSuccessListener<DocumentReference?> { }).addOnFailureListener(
//                OnFailureListener { })


        val clickme = findViewById<Button>(R.id.Next)
        val clickme2 = findViewById<Button>(R.id.Signin)

        clickme.setOnClickListener{
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)

        }

        clickme2.setOnClickListener{
            val intent2 = Intent(this, Login::class.java)
            startActivity(intent2)

        }

//        clickme.setOnClickListener {
//            dialog.show(supportFragmentManager,"")
//        }




    }


}