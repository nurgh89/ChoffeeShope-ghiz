package com.android.coffeshop.Customer

import android.annotation.SuppressLint

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

import android.widget.ImageView
import android.widget.TextView
import com.android.coffeshop.R

import com.android.coffeshop.product
import com.squareup.picasso.Picasso


@Suppress("UNREACHABLE_CODE", "NAME_SHADOWING")
class AdapterCust(val Ctx: Context, val LayoutId: Int, val menulist: List<product>)
    : ArrayAdapter<product>(Ctx, LayoutId, menulist) {


    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(Ctx)
        val view: View = layoutInflater.inflate(LayoutId, null)



        val txt_NameChart = view.findViewById<TextView>(R.id.cust_name)
        val txt_JenisChart = view.findViewById<TextView>(R.id.cust_jenis_product)
        val DeskripsiChart = view.findViewById<TextView>(R.id.cust_deskripsi)
        val txttHarga = view.findViewById<TextView>(R.id.cust_harga_product)
        val gambar = view.findViewById<ImageView>(R.id.custom_cust_img_view)

//
//        val Btn_Cart = view.findViewById<FloatingActionButton>(R.id.shopingcart)
//
//        Btn_Cart.setOnClickListener {
//
//            ShowUpCart()
//        }


        val user = menulist[position]

        txt_NameChart.text = user.nama_product
        txt_JenisChart.text = user.jenis_product
        txttHarga.text = user.harga_product
        DeskripsiChart.text = user.deskripsi

        Picasso.get().load(user.image).into(gambar)

        if (user.image!!.isEmpty()) {
            gambar.setImageResource(R.drawable.ic_launcer_background)
        } else{
            Picasso.get().load(user.image).into(gambar)
        }


        return view
    }

//    private fun ShowUpCart(){
//
//        val builder = AlertDialog.Builder(Ctx)
//
//        builder.setTitle("Tambahkan Keranjang")
//
//        val inflater = LayoutInflater.from(Ctx)
//
//        val view = inflater.inflate(R.layout.activity_quantity, null)
//
//        val text_name_product = view.findViewById<TextInputEditText>(R.id.add_nama_product)
//        val txt_harga_product = view.findViewById<TextInputEditText>(R.id.add_jenis_product)
//
//        val tambah_button = view.findViewById<Button>(R.id.increment_button)
//        val kurang_button = view.findViewById<Button>(R.id.decrement_button)





    }
