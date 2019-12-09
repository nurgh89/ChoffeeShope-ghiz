package com.android.coffeshop

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.drawable.AnimationDrawable

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.coffeshop.Admin.DashboardAdminBambankActivity
import com.android.coffeshop.Customer.DashboardCustomerActivity
import com.google.firebase.auth.FirebaseAuth


import kotlinx.android.synthetic.main.activity_login.*


class Login_Activity: AppCompatActivity() {

    internal lateinit var auth: FirebaseAuth
    var TAG = "Login Activity"
    private var progressDialog: ProgressDialog? = null
    lateinit var animationDrawable: AnimationDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        animationDrawable = laylogin.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(3000)
        animationDrawable.setExitFadeDuration(3000)
        animationDrawable.start()

        auth = FirebaseAuth.getInstance()

        btn_login.setOnClickListener {


            val mail = edt_user.text.toString()
            val pass = edt_pass.text.toString()


            if (mail.isEmpty() || pass.isEmpty()){
                Log.d(TAG, "is empty colum")
                Toast.makeText(this, "Tidak boleh kosong Bambank", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }else if (pass.length < 6){
                Log.d(TAG, "password nya kurang")
                Toast.makeText(this, "Kurang Tuh Password nya", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }else{

                if (mail == "bambankuser@gmail.com" || pass == "adminuser"){

                    val intent = Intent(this, DashboardAdminBambankActivity::class.java)
                    startActivity(intent)

                    Log.d(TAG, "acitvity admin")
                }else  {

                      auth.signInWithEmailAndPassword(mail, pass)
                          .addOnCompleteListener {
                              if (it.isSuccessful){



                                  Log.d(TAG, "Successfully  Login ")

                                  startActivity(Intent(this, DashboardCustomerActivity::class.java))
                                  finish()
                              }else{

                                  

                                  Log.d(TAG, "Failed Login")
                                  Toast.makeText(this,
                                      "Anda Gagal untuk masuk, silahkan Sign Up terlebih dahulu atau coba lagi",
                                      Toast.LENGTH_SHORT
                                  ).show()

                              }
                          }


                }



            }

        }
        btnatas.setOnClickListener {

            Log.d(TAG, "Try to Sign Up")

            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        btnbawah.setOnClickListener {

            Log.d(TAG, "Try to Forgot Password")
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

    }
}