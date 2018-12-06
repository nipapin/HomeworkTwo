package com.homeworktwo.papin.homeworktwo

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.support.v4.content.FileProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

private lateinit var mCurrentPhotoPath: String
private var camWasOpen = false
private var us:Long = 0
private lateinit var photoURI : Uri

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val reqTakePhoto = 1

        @Throws(IOException::class)

        fun createImageFile(): File {

            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            return File.createTempFile(
                "JPEG_${timeStamp}_",
                ".jpg",
                storageDir
            ).apply {
                mCurrentPhotoPath = absolutePath
            }
        }

        fun takePhoto() {
            camWasOpen = true
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent -> takePictureIntent.resolveActivity(packageManager)?.also {

                    val photoFile:File? = try {
                        createImageFile()
                    } catch (ex: IOException) {
                        null
                    }

                    photoFile?.also {
                        photoURI = FileProvider.getUriForFile(this, "com.homeworktwo.papin.homeworktwo.fileprovider", it)
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, reqTakePhoto)
                    }

                }
            }
        }

        camButton.setOnClickListener {
            if (inputName.text.isNotEmpty()) {
                takePhoto()
            } else {
                Snackbar.make(main_layout, getString(R.string.message), Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        if(camWasOpen) {
            camWasOpen = false
            val intentResult = Intent(this, Main2Activity::class.java)
                intentResult.data = photoURI
                intentResult.putExtra("userName", inputName.text.toString())
                intentResult.putExtra("size", photoURI.toString())
            startActivity(intentResult)
        }
    }
}