package com.example.mykonter.api

import com.example.mykonter.model.Servis
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("api.php")
    fun getServis(
        @Query("aksi") aksi: String = "list"
    ): Call<List<Servis>>

    @GET("api.php")
    fun getSaldo(
        @Query("aksi") aksi: String = "saldo"
    ): Call<Map<String, Long>>

    @POST("api.php")
    fun insertServis(
        @Query("aksi") aksi: String = "insert",
        @Query("nama_konsumen") nama: String,
        @Query("tipe_hp") tipe: String,
        @Query("kerusakan") kerusakan: String,
        @Query("harga") harga: String
    ): Call<Map<String, String>>

    @POST("api.php")
    fun ubahStatus(
        @Query("aksi") aksi: String = "ubah_status",
        @Query("id") id: String,
        @Query("status") status: String
    ): Call<Map<String, String>>



}
