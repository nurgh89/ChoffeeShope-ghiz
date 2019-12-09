package com.android.coffeshop

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.coffeshop.Customer.DashboardCustomerActivity
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class   RegisterActivity : AppCompatActivity() {

    val TAG = "RegisterActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var animationDrawable: AnimationDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        animationDrawable = layerregister.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(3000)
        animationDrawable.setExitFadeDuration(3000)
        animationDrawable.start()

        btn_register.setOnClickListener {

            PerformRegist()
        }
    }

    private fun PerformRegist() {

        val username = edt_usernameregis.text.toString()
        val email = edt_emailregis.text.toString()
        val password = edt_passregis.text.toString()

        if (email.isEmpty() || password.isEmpty()|| username.isEmpty()) {
            Toast.makeText(this, "Kolom tidak Boleh Kosong", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d(TAG, "Email is : " + email)
        Log.d(TAG, "Password is : $password")

        progressDialog = ProgressDialog(this)
        progressDialog!!.setCancelable(false)
        progressDialog!!.setMessage("Proses")
        progressDialog!!.show()

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    //untuk menampilkan token uid firebase database
                    Log.d(TAG, "proses Auth behasil dengan uid is: ${it.result?.user?.uid}")

                    val intent = Intent(this, DashboardCustomerActivity::class.java)
                    startActivity(intent)

                    progressDialog!!.hide()

                    saveDataOnFirebaseDatabase()


                }
            }.addOnFailureListener {

                Log.d(TAG, "it's Failed to Create Users: ${it.message}")

                Toast.makeText(this, "Anda Gagal membuat Akun : ${it.message}", Toast.LENGTH_SHORT).show()

            }

    }



    private fun saveDataOnFirebaseDatabase(){

        val uid = FirebaseAuth.getInstance().uid?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val users = Users(uid, edt_emailregis.text.toString(), edt_passregis.text.toString())

        progressDialog = ProgressDialog(this)
        progressDialog!!.isIndeterminate = true
        progressDialog!!.setMessage("Registering..")


        ref.setValue(users)
           .addOnCompleteListener {


               if (it.isSuccessful){



                   Log.d(TAG, "Anda behasil membuat Akun")

                   val intent = Intent(this, DashboardCustomerActivity::class.java)
                   startActivity(intent)
                   progressDialog!!.hide()
                   finish()
               }

           }.addOnFailureListener {

               Log.d(TAG, "Anda Gagal membuat Akun bambank ${it.message}")
               progressDialog!!.hide()
               Toast.makeText(this, "Anda gagal membuat Akun Bambank", Toast.LENGTH_SHORT).show()
           }


    }

    //database user
   data class Users  (val uid: String, val email: String, val password: String)

}


