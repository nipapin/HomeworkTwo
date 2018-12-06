package com.homeworktwo.papin.homeworktwo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main2.*
import java.io.File

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val userName = intent.getStringExtra("userName")

        userNameFromInput.text = userName

        val picturePath = intent.data
        userPhoto.setImageURI(picturePath)

        val fileSize = File(picturePath?.toString()).exists()

        Toast.makeText(this, fileSize.toString(), Toast.LENGTH_LONG).show()


    }
}
