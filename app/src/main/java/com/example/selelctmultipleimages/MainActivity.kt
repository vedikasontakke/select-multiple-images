package com.example.selelctmultipleimages

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()
{
    private var images :ArrayList<Uri>? = null
    private var position = 0
    private val PICK_IMAGES_CODE = 0


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onCreate(savedInstanceState: Bundle?)
    {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        images = ArrayList()

        imgSwitchwer.setFactory { ImageView(applicationContext) }

        pickImgBtn.setOnClickListener {

            pickImagesIntent()
        }

        next_btn.setOnClickListener {

            if(position < images!!.size-1){

                position++

                imgSwitchwer.setImageURI(images!![position])

            }
            else
            {
                Toast.makeText(this , "no more images" , Toast.LENGTH_SHORT).show()
            }
        }

        prv_btn.setOnClickListener {

            if(position > 0 ){

                position--

                imgSwitchwer.setImageURI(images!![position])
            }
            else
            {
                Toast.makeText(this , "no more images" , Toast.LENGTH_SHORT).show()

            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private fun pickImagesIntent(){

        val intent = Intent()

        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE , true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent , "select image(s)"), PICK_IMAGES_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == PICK_IMAGES_CODE){

            if(resultCode == Activity.RESULT_OK){

                if(data!!.clipData != null){

                    val count = data.clipData!!.itemCount

                    for(i in 0 until  count) {

                        val imageUri = data.clipData!!.getItemAt(i).uri

                        images!!.add(imageUri)
                    }

                    imgSwitchwer.setImageURI(images!![0])
                    position = 0
                }
                else
                {
                    val imageUri = data.data

                    imgSwitchwer.setImageURI(imageUri)
                    position = 0
                }
            }
        }
    }
}