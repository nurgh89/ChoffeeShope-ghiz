package com.android.coffeshop.Admin

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast

import com.android.coffeshop.R
import com.android.coffeshop.product


import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_add_product_dash.*
import kotlinx.android.synthetic.main.activity_splash.*


class Add_Product : AppCompatActivity(), View.OnClickListener {
    private var imgPath: Uri? = null
    lateinit var ref: DatabaseReference

    lateinit var animationDrawable: AnimationDrawable



    override fun onClick(v: View?) {
        when (v) {
            choose_image -> {
                val iImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(iImg, 0)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product_dash)

        choose_image.setOnClickListener(this)


        animationDrawable = layer_add.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(3000)
        animationDrawable.setExitFadeDuration(3000)
        animationDrawable.start()

        ref = FirebaseDatabase.getInstance().getReference("product")

        btn_save.setOnClickListener {
            savedata()
            val intent = Intent(this, DashboardAdminBambankActivity::class.java)
            startActivity(intent)
        }
    }

    private fun savedata() {

        val storageRef = FirebaseStorage
            .getInstance()
            .getReference("images")


        storageRef.putFile(imgPath!!)
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener {

                    val name_product = add_nama_product.text.toString()
                    val jenis_product = add_jenis_product.text.toString()
                    val deskripsi = add_deskripsi.text.toString()
                    val harga_product = add_harga_product.text.toString()
                    val image = it.toString()

                    val productId = ref.push().key.toString()

                    val product = product(
                        productId,
                        name_product,
                        jenis_product,
                        harga_product,
                        deskripsi,
                        image
                    )

                    ref.child(productId).setValue(product).addOnCompleteListener {
                        Toast.makeText(this, "Successs", Toast.LENGTH_SHORT).show()
                        add_nama_product.setText("")
                        add_jenis_product.setText("")
                        add_harga_product.setText("")
                        add_deskripsi.setText("")

                    }


                    Toast.makeText(this, "Add Image Successfully", Toast.LENGTH_SHORT).show()
                    finish()

                }
            }
            .addOnFailureListener {
                println("Info File : ${it.message}")
            }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            imgPath = data?.data


        }
    }


}

