package com.example.mykonter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mykonter.BentukData.Servis
import com.example.mykonter.api.ApiService
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon



private val Icons.Filled.History: Any

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2/mykonter/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiService::class.java)

        setContent {
            var tab by remember { mutableStateOf(0) }


            var servisList by remember { mutableStateOf<List<Servis>>(emptyList()) }
            var saldo by remember { mutableStateOf(0L) }

            LaunchedEffect(Unit) {

                // Ambil daftar servis
                api.getServis().enqueue(object : Callback<List<Servis>> {
                    override fun onResponse(
                        call: Call<List<Servis>>,
                        response: Response<List<Servis>>
                    ) {
                        if (response.isSuccessful) {
                            servisList = response.body() ?: emptyList()
                        }
                    }
                    override fun onFailure(call: Call<List<Servis>>, t: Throwable) {}
                })

                // Ambil saldo
                api.getSaldo().enqueue(object : Callback<Map<String, Long>> {
                    override fun onResponse(
                        call: Call<Map<String, Long>>,
                        response: Response<Map<String, Long>>
                    ) {
                        saldo = response.body()?.get("saldo") ?: 0
                    }
                    override fun onFailure(call: Call<Map<String, Long>>, t: Throwable) {}
                })
            }

            val filteredList = when (tab) {
                0 -> servisList.filter { it.status == "in-progress" }
                1 -> servisList.filter { it.status == "done" }
                else -> servisList.filter { it.status == "taken" }
            }


            MyKonterScreen(filteredList, saldo, api, tab) {
                tab = it
            }


        }

    }
}


@Composable
fun MyKonterScreen(
    servisList: List<Servis>,
    saldo: Long,
    api: ApiService,
    tab: Int,
    onTabChange: (Int) -> Unit
) {
    Column {

        // SALDO
        Text(
            text = "Saldo: Rp $saldo",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        // MENU BAWAH
        NavigationBar {
            NavigationBarItem(
                selected = tab == 0,
                onClick = { onTabChange(0) },
                icon = { Icon(Icons.Default.List, null) },
                label = { Text("Antrian") }
            )
            NavigationBarItem(
                selected = tab == 1,
                onClick = { onTabChange(1) },
                icon = { Icon(Icons.Default.Done, null) },
                label = { Text("Siap") }
            )
            NavigationBarItem(
                selected = tab == 2,
                onClick = { onTabChange(2) },
                icon = { Icon(Icons.Default.History, null) },
                label = { Text("Riwayat") }
            )
        }

        // LIST SERVIS
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(servisList) { servis ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {

                        Text(servis.nama_konsumen)
                        Text(servis.tipe_hp)
                        Text("Rp ${servis.harga}")
                        Text("Status: ${servis.status}")

                        Spacer(Modifier.height(6.dp))

                        when (servis.status) {
                            "in-progress" -> {
                                Button(onClick = {
                                    api.ubahStatus(servis.id, "done").enqueue(
                                        object : Callback<Map<String, String>> {
                                            override fun onResponse(
                                                call: Call<Map<String, String>>,
                                                response: Response<Map<String, String>>
                                            ) {}
                                            override fun onFailure(
                                                call: Call<Map<String, String>>,
                                                t: Throwable
                                            ) {}
                                        }
                                    )
                                }) { Text("SELESAI") }
                            }

                            "done" -> {
                                Button(onClick = {
                                    api.ubahStatus(servis.id, "taken").enqueue(
                                        object : Callback<Map<String, String>> {
                                            override fun onResponse(
                                                call: Call<Map<String, String>>,
                                                response: Response<Map<String, String>>
                                            ) {}
                                            override fun onFailure(
                                                call: Call<Map<String, String>>,
                                                t: Throwable
                                            ) {}
                                        }
                                    )
                                }) { Text("DIAMBIL") }
                            }
                        }
                    }
                }
            }
        }
    }
}
