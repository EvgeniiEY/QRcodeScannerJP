package com.example.qrcodescannerjp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.qrcodescannerjp.ui.theme.QRcodeScannerJPTheme

class GroupIdResultActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface {
                val viewModel: MainViewModel = viewModel
                viewModel.fetchRowsBySupportId(12) // Fetch rows on creation
                SupportIdListScreen(viewModel)
            }


        }
    }
}

@Composable
fun SupportIdListScreen(viewModel: MainViewModel ) {
    val supportIdRows by viewModel.supportIdRows.observeAsState(listOf())



    LazyColumn(
        Modifier.fillMaxSize()
    ){
        item { androidx.compose.material.Text("Все ДКС группы:", fontSize = 20.sp, color = Color.Blue) }
        items(supportIdRows){lang -> androidx.compose.material.Text(lang, fontSize = 16.sp) }
    }
}