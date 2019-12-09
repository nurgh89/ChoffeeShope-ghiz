package com.android.coffeshop.Customer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.android.coffeshop.Admin.DashboardAdminBambankActivity
import com.android.coffeshop.Login_Activity
import com.android.coffeshop.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_dashboard_customer.*


class DashboardCustomerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_customer)

        val auth = FirebaseAuth.getInstance().currentUser


        menu.setOnClickListener {

            val intent = Intent(this, DashboardCustomerSecondActivity::class.java)
            startActivity(intent)
        }

        btnexit.setOnClickListener {
            startActivity(
                Intent(
                    this@DashboardCustomerActivity,
                    Login_Activity::class.java
                ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
        }
    }
}
