package com.android.coffeshop.Admin

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.android.coffeshop.Constant
import com.android.coffeshop.Customer.DashboardCustomerActivity
import com.android.coffeshop.Customer.DashboardCustomerSecondActivity

import com.android.coffeshop.R
import com.android.coffeshop.product
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText


import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

@Suppress("UNREACHABLE_CODE", "NAME_SHADOWING")
class Adapter(val mCtx: Context, val layoutResId: Int, val list: List<product> )
    : ArrayAdapter<product>(mCtx,layoutResId,list) {

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val textNameProduct = view.findViewById<TextView>(R.id.name)
        val textJenisProduct = view.findViewById<TextView>(R.id.jenis_product)
        val textDeskripsi = view.findViewById<TextView>(R.id.deskripsi)
        val textHargaProduct = view.findViewById<TextView>(R.id.harga_product)
        val image = view.findViewById<ImageView>(R.id.custom_img_view)


        val btnEdit = view.findViewById<FloatingActionButton>(R.id.edit)
        val btnDelete = view.findViewById<FloatingActionButton>(R.id.delete)

        val user = list[position]

        textNameProduct.text = user.nama_product
        textJenisProduct.text = user.jenis_product
        textHargaProduct.text = user.harga_product
        textDeskripsi.text = user.deskripsi



        if (user.image!!.isEmpty()) {
            image.setImageResource(R.drawable.ic_launcer_background);
        } else{
            Picasso.get().load(user.image).into(image);
        }


        btnEdit.setOnClickListener {
            showUpdateDialog(user)
        }
        btnDelete.setOnClickListener {
            Deleteinfo(user)
        }

        return view
    }

    private fun Deleteinfo(user: product) {
        val progressDialog = ProgressDialog(
            context,
            R.style.Theme_MaterialComponents_Light_Dialog
        )
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Deleting...")
        progressDialog.show()
        val mydatabase = FirebaseDatabase.getInstance().getReference("product")
        mydatabase.child(user.id).removeValue()
        Toast.makeText(mCtx, "Deleted!!", Toast.LENGTH_SHORT).show()
        val intent = Intent(context, DashboardAdminBambankActivity::class.java)
        context.startActivity(intent)
    }

    private fun showUpdateDialog(user: product) {
        val builder = AlertDialog.Builder(mCtx)

        builder.setTitle("Update")

        val inflater = LayoutInflater.from(mCtx)

        val view = inflater.inflate(R.layout.activity_add_product_dash, null)

        val textInputNamaProduct_ = view.findViewById<TextInputEditText>(R.id.add_nama_product)
        val textInputJenisProduct = view.findViewById<TextInputEditText>(R.id.add_jenis_product)
        val textInputDeskripsiProduct = view.findViewById<TextInputEditText>(R.id.add_deskripsi)
        val textInputHargaProduct = view.findViewById<TextInputEditText>(R.id.add_harga_product)




        textInputNamaProduct_.setText(user.nama_product)
        textInputJenisProduct.setText(user.jenis_product)
        textInputDeskripsiProduct.setText(user.deskripsi)
        textInputHargaProduct.setText(user.harga_product)



        builder.setView(view)

        builder.setPositiveButton("Update") { dialog, which ->

            val dbProduct = FirebaseDatabase.getInstance().getReference("product")

            val nama_product = textInputNamaProduct_.text.toString().trim()

            val jenis_product = textInputJenisProduct.text.toString().trim()

            val deskripsi = textInputDeskripsiProduct.text.toString().trim()

            val harga_product = textInputHargaProduct.text.toString().trim()


            if (nama_product.isEmpty()) {
                textInputNamaProduct_.error = "please enter name"
                textInputNamaProduct_.requestFocus()
                return@setPositiveButton
            }

            if (jenis_product.isEmpty()) {
                textInputJenisProduct.error = "please enter jenis product"
                textInputJenisProduct.requestFocus()
                return@setPositiveButton
            }

            if (deskripsi.isEmpty()) {
                textInputDeskripsiProduct.error = "please enter deskripsi "
                textInputDeskripsiProduct.requestFocus()
                return@setPositiveButton
            }

            if (harga_product.isEmpty()) {
                textInputHargaProduct.error = "please enter harga product"
                textInputHargaProduct.requestFocus()
                return@setPositiveButton
            }

            val imagekosong : String? = null

            val user = product(user.id, nama_product, jenis_product, harga_product, deskripsi, imagekosong)

            dbProduct.child(user.id).setValue(user).addOnCompleteListener {
                Toast.makeText(mCtx, "Updated", Toast.LENGTH_SHORT).show()
            }

        }

        builder.setNegativeButton("No") { dialog, which ->

        }

        val alert = builder.create()
        alert.show()

    }
}

