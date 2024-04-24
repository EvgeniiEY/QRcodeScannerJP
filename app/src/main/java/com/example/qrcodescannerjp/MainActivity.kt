package com.example.qrcodescannerjp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrcodescannerjp.ui.theme.QRcodeScannerJPTheme
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import com.parse.ParseObject
import androidx.compose.ui.window.Dialog as Dialog


class MainActivity : ComponentActivity() {
    private var dksid = 0
    lateinit var resultAfterScan: String
    private val viewModel by viewModels<MainViewModel>()

    //put qr in base function
    private val scanLauncher = registerForActivityResult(
        ScanContract()
    ) { result ->
        if (result.contents == null) {
        } else {
            Toast.makeText(this, "QR код: ${result.contents}", Toast.LENGTH_LONG).show()

            val secondObject = ParseObject("FirstClass")
            val qrArray = result.contents.split("_").toTypedArray()
            resultAfterScan = result.contents
            findDuplicate(resultAfterScan)

            Toast.makeText(this, findDuplicate(result.contents), Toast.LENGTH_LONG).show()
            //todo: return result as a dialog

            secondObject.put("Mark", qrArray[0])
            secondObject.put("DKSId", qrArray[1].toInt())
            secondObject.put("SupportId", qrArray[2].toInt())
            secondObject.put("Manufacture", qrArray[3])
            secondObject.put("DateProduced", qrArray[4])

            secondObject.saveInBackground {
                if (it != null) {
                    it.localizedMessage?.let { message -> Log.e("MainActivity", message) }
                } else {
                    Log.d("MainActivity", "Object saved.")
                }
            }
        }
    }


    private val scanFinder = registerForActivityResult(
        ScanContract()
    ) { result ->
        if (result.contents == null) {
        } else {
            Toast.makeText(this, "QR код: ${result.contents}", Toast.LENGTH_LONG).show()
            resultAfterScan = result.contents
            //Передаю строку в ф-ю

            findAndChange(resultAfterScan = resultAfterScan)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            QRcodeScannerJPTheme {
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

