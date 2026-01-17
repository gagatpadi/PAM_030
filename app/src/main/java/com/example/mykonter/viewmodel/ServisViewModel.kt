package com.example.mykonter.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.mykonter.model.Servis
import com.example.mykonter.repository.ServisRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServisViewModel : ViewModel() {

    private val repository = ServisRepository()

    val servisList = mutableStateListOf<Servis>()

    fun loadServis() {
        repository.getServis().enqueue(object : Callback<List<Servis>> {
            override fun onResponse(
                call: Call<List<Servis>>,
                response: Response<List<Servis>>
            ) {
                if (response.isSuccessful) {
                    servisList.clear()
                    servisList.addAll(response.body() ?: emptyList())
                }
            }

            override fun onFailure(call: Call<List<Servis>>, t: Throwable) {}
        })
    }
}
