package com.android.coffeshop.Admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.android.coffeshop.R
import com.android.coffeshop.product
import com.google.firebase.database.*
import com.google.android.material.floatingactionbutton.FloatingActionButton as FloatingActionButton1


class DashboardAdminBambankActivity : AppCompatActivity() {


    lateinit var ref : DatabaseReference
    lateinit var list : MutableList<product>
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_product)


        ref = FirebaseDatabase.getInstance().getReference("product")
        list = mutableListOf()
        listView = findViewById(R.id.listview)

        val btn_tmabh = findViewById<FloatingActionButton1>(R.id.add_product)
        btn_tmabh.setOnClickListener {

            val intent = Intent(this, Add_Product::class.java)
            startActivity(intent)
        }


        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()){

                    list.clear()
                    for (h in p0.children){
                        val user = h.getValue(product::class.java)
                        list.add(user!!)
                    }
                    val adapter = Adapter(
                        this@DashboardAdminBambankActivity,
                        R.layout.activity_custom_order,
                        list
                    )
                    listView.adapter = adapter
                }
            }
        })

    }
}
