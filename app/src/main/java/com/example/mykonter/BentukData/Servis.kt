package com.example.mykonter.BentukData

data class Servis(
    val id: String,
    val nama_konsumen: String,
    val tipe_hp: String,
    val kerusakan: String,
    val harga: String,
    val status: String,
    val tgl_masuk: String,
    val tgl_ambil: String?
)
