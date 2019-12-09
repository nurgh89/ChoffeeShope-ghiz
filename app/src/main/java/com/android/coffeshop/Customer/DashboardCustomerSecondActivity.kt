package com.android.coffeshop.Customer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.coffeshop.Admin.Adapter
import com.android.coffeshop.Admin.Add_Product
import com.android.coffeshop.Admin.DashboardAdminBambankActivity
import com.android.coffeshop.R
import com.android.coffeshop.product
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_update_product.*
import java.util.Collections.list


class DashboardCustomerSecondActivity: AppCompatActivity() {

    lateinit var database: DatabaseReference
    lateinit var list : MutableList<product>
    lateinit var listView: ListView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_custkedua)


        list = mutableListOf()
        listView = findViewById(R.id.listmenu)


        database.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()){

                    list.clear()
                    for (h in p0.children){
                        val user = h.getValue(product::class.java)
                        list.add(user!!)
                    }
                    val adapter = AdapterCust(
                        this@DashboardCustomerSecondActivity,
                        R.layout.activity_custom_cust,
                        list
                    )
                    listView.adapter = adapter
                }
            }
        })

    }


}