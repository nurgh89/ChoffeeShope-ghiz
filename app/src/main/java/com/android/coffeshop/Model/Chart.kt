package com.android.coffeshop.Model

class Chart(

    var ChartId: String,
    var namechart: String,
    var harga: String,
    var jenis: String,
    var deskripsichart: String,
    var Images: String)
{

    constructor(): this(

        "",
        "",
        "",
        "",
        "",
        ""){

    }
}