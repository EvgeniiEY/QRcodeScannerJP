package com.example.qrcodescannerjp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.qrcodescannerjp.ui.theme.QRcodeScannerJPTheme
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import com.parse.ParseObject

class MainActivity : ComponentActivity() {
    private val scanLauncher = registerForActivityResult(
        ScanContract()
    ) { result ->
        if (result.contents == null) {
        } else {
        Toast.makeText(this, "QR код: ${result.contents}", Toast.LENGTH_LONG).show()
            val secondObject = ParseObject("FirstClass")
            secondObject.put("message","${result.contents}")
            secondObject.saveInBackground {
                if (it != null){
                    it.localizedMessage?.let { message -> Log.e("MainActivity", message) }
                }else{
                    Log.d("MainActivity","Object saved.")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            QRcodeScannerJPTheme() {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {
                        scan()
                    }) {
                        Text(text = "СКАН")
                    }
                }
            }
        }
        val firstObject = ParseObject("FirstClass")
        firstObject.put("message","Hey ! First message from android. Parse is now connected")
        firstObject.saveInBackground {
            if (it != null){
                it.localizedMessage?.let { message -> Log.e("MainActivity", message) }
            }else{
                Log.d("MainActivity","Object saved.")
            }
        }


    }

    private fun scan() {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        options.setPrompt("Отсканируйте QR-код")
        options.setCameraId(0)
        options.setBeepEnabled(false)
        options.setBarcodeImageEnabled(true)
        scanLauncher.launch(options)
    }
}

















//        setContent {
//            Column() {
//                ListItem("Evgenii Yakhontov", "Programmer")
//                ListItem("Evgenii Yakhontov", "Programmer")
//                ListItem("Evgenii Yakhontov", "Programmer")
//                ListItem("Evgenii Yakhontov", "Programmer")
//                ListItem("Evgenii Yakhontov", "Programmer")
//                ListItem("Evgenii Yakhontov", "Programmer")
//            }
//        }
//    }
//}
//
//@Composable
//private fun ListItem(name:String, prof:String){
//    Card(
//        modifier = Modifier
//
//            .fillMaxWidth()
//            .padding(10.dp),
//        shape = RoundedCornerShape(15.dp),
//        elevation = 5.dp
//    ){
//        Box() {
//
//            Row(){
//                Image(painter = painterResource(id = R.drawable.mol),
//                    contentDescription = "image",
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier
//                        .padding(5.dp)
//                        .size(64.dp)
//                        .clip(CircleShape)
//                )
//                Column(modifier = Modifier.padding()) {
//                    Text(text = name)
//                    Text(text = prof)
//                }
//
//            }
//        }
//    }
//}