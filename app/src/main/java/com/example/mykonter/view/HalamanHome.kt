package com.example.mykonter.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mykonter.viewmodel.ServisViewModel

@Composable
fun HalamanHome(navController: NavController) {

    val viewModel: ServisViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.loadServis()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Text(
            text = "ANTRIAN SERVIS",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (viewModel.servisList.isEmpty()) {
            Text("Tidak ada data servis")
        } else {
            LazyColumn {
                items(viewModel.servisList) { servis ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("Nama: ${servis.nama_konsumen}")
                            Text("HP: ${servis.tipe_hp}")
                            Text("Harga: Rp ${servis.harga}")
                            Text("Status: ${servis.status}")
                        }
                    }
                }
            }
        }
    }
}
