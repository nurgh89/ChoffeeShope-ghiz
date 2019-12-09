package com.android.coffeshop.Model

class Order(var OrderId: String, var name_product: String, var jumlah: String, var alamat: String, var no_tlpn: String) {

    constructor(): this ("", "", "", "", ""){

    }
}