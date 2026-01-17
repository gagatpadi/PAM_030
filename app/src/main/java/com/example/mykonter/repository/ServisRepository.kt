package com.example.mykonter.repository

import com.example.mykonter.api.ApiConfig
import com.example.mykonter.model.Servis
import retrofit2.Call

class ServisRepository {

    fun getServis(): Call<List<Servis>> {
        return ApiConfig.getApiService().getServis()
    }
}
