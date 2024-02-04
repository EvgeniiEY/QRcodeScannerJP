package com.example.qrcodescannerjp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.qrcodescannerjp.ui.theme.QRcodeScannerJPTheme
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import com.parse.ParseObject
import com.parse.ParseQuery


class MainActivity : ComponentActivity() {
    private var dksid = 0

    //put qr in base function
    private val scanLauncher = registerForActivityResult(
        ScanContract()
    ) { result ->
        if (result.contents == null) {
        } else {
            Toast.makeText(this, "QR код: ${result.contents}", Toast.LENGTH_LONG).show()

            val secondObject = ParseObject("FirstClass")
            secondObject.put("message", "${result.contents}")
            secondObject.put("DKSId", "${dksid}")
            secondObject.saveInBackground {
                if (it != null) {
                    it.localizedMessage?.let { message -> Log.e("MainActivity", message) }
                } else {
                    Log.d("MainActivity", "Object saved.")
                }
            }
        }
    }

//    private val scanAlarm = doQueryAlarm(
//        val query = ParseQuery<Parse>
//
//        ScanContract()
//    ) { result ->
//        if (result.contents == null) {
//        } else {
//
//        }
    // find number query

    private val scanFinder = registerForActivityResult(
        ScanContract()
    ) { result ->
        if (result.contents == null) {
        } else {
            Toast.makeText(this, "QR код: ${result.contents}", Toast.LENGTH_LONG).show()

            val query = ParseQuery.getQuery<ParseObject>("FirstClass")

            query.getInBackground(
                "NpiB98yZz0"
            ) { alarm, e ->
                if (e == null) {

                    alarm.put("Alarm", true)
                    alarm.saveInBackground()
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
                            scanAlarm()
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Blue,
                            contentColor = Color.Red
                        )
                    )
                    {
                        Text(text = "АВАРИЯ", fontSize = 20.sp)
                    }

                    Button(

                        onClick = {
                            scan()
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Blue,
                            contentColor = Color.White
                        )
                    )
                    {
                        Text(text = "СКАН Запрос", fontSize = 20.sp)
                    }

                    Button(

                        onClick = {
                            scan()
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Blue,
                            contentColor = Color.White
                        )
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

    private fun scanAlarm() {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        options.setPrompt("Отсканируйте QR-код")
        options.setCameraId(0)
        options.setBeepEnabled(true)
        options.setBarcodeImageEnabled(true)
        scanFinder.launch(options)
    }
}
//        private fun doQueryAlarm() {
//            val query = ParseQuery<ParseObject>("FirstClass")
//            query.whereContains("DKSId", "3")
//            val result = ParseObject("FirstClass")
//            result.put("Alarm", "true")
//        }
//    }


//private val scanFinder = registerForActivityResult(
//    ScanContract()
//) { result ->
//    if (result.contents == null) {
//    } else {
//        Toast.makeText(this, "QR код: ${result.contents}", Toast.LENGTH_LONG).show()
//
//        val query = ParseQuery.getQuery<ParseObject>("FirstClass")
//
//        query.getInBackground("783WeFkftA") { Message, e ->
//            if (e == null) {
//                Message.put("Message", "ЖОПА ХУЙ ЕБЛО !!! ")
//
//                Message.saveInBackground()
//            }
//        }
//
//    }
//}

