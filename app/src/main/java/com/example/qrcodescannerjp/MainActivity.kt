package com.example.qrcodescannerjp

import android.graphics.drawable.shapes.Shape
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.qrcodescannerjp.ui.theme.QRcodeScannerJPTheme
import com.example.qrcodescannerjp.ui.theme.Shapes
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import com.parse.ParseObject

class MainActivity : ComponentActivity() {
    private var dksid = 0
    private val scanLauncher = registerForActivityResult(
        ScanContract()
    ) { result ->
        if (result.contents == null) {
        } else {
            Toast.makeText(this, "QR код: ${result.contents}", Toast.LENGTH_LONG).show()

            val secondObject = ParseObject("FirstClass")
            secondObject.put("message", "${result.contents}")
            secondObject.put("DKSId", "${dksid++}")
            secondObject.saveInBackground {
                if (it != null) {
                    it.localizedMessage?.let { message -> Log.e("MainActivity", message) }
                } else {
                    Log.d("MainActivity", "Object saved.")
                }
            }
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            QRcodeScannerJPTheme() {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    Button(

                        onClick = {
                            scan()
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue, contentColor = Color.Red)
                    )
                    {
                        Text(text = "АВАРИЯ", fontSize = 20.sp)
                    }

                    Button(

                        onClick = {
                            scan()
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue, contentColor = Color.White)
                    )
                    {
                        Text(text = "СКАН Запрос", fontSize = 20.sp)
                    }

                    Button(

                        onClick = {
                            scan()
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue, contentColor = Color.White)
                    )
                    {
                        Text(text = "СКАН Внести QR код в базу", fontSize = 20.sp)
                    }

                }
            }
        }
//        val firstObject = ParseObject("FirstClass")
//        firstObject.put("message","Hey ! First message from android. Parse is now connected")
//        firstObject.saveInBackground {
//            if (it != null){
//                it.localizedMessage?.let { message -> Log.e("MainActivity", message) }
//            }else{
//                Log.d("MainActivity","Object saved.")
//            }
//        }
    }

    private fun scan() {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        options.setPrompt("Отсканируйте QR-код")
        options.setCameraId(0)
        options.setBeepEnabled(true)
        options.setBarcodeImageEnabled(true)
        scanLauncher.launch(options)
    }
}