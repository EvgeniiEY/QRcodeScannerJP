package com.example.qrcodescannerjp

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.parse.ParseException
import com.parse.ParseObject
import com.parse.ParseQuery
import java.util.Date


//        private const val TAG = "QueryRetrievers"

//        fun queryFind() {
//            //This find function works synchronously.
//            val query = ParseQuery<ParseObject>("Profile")
//            try {
//                val list = query.find()
//                Log.d(Companion.TAG, "List: $list")
//            } catch (e: ParseException) {
//                e.printStackTrace()
//            }
//        }

    //        fun queryEqualTo() {
//            val query = ParseQuery<ParseObject>("FirstClass")
//            query.whereEqualTo("DKSId", 999999)
//            query.findInBackground { objects: List<ParseObject>, e: ParseException? ->
//                if (e == null) {
//                    Log.d(Companion.TAG, "Objects: $objects")
//
//                } else {
//                    Log.e(Companion.TAG, "Parse Error: ", e)
//                }
//            }
//        }
//функция установки статуса Авария на true

    fun findAndChange(resultAfterScan: String) {
        // Replace with your actual class name used in Parse
        val className = "FirstClass"
        val objectIdToFind = resultAfterScan.split("_").toTypedArray()
        val newAlarmStatus = true

// Create a query for the Parse class
        val query = ParseQuery.getQuery<ParseObject>(className)

// Query for objects with the specific name
        query.whereEqualTo("DKSId", objectIdToFind[1].toInt())
        query.getFirstInBackground { parseObject, e ->
            if (e == null) {
                // Object was successfully retrieved
                // Update the boolean attribute
                parseObject.put("Alarm", newAlarmStatus)

                // Save the updated object back to the server
                parseObject.saveInBackground { saveException ->
                    if (saveException == null) {
                        // The object was saved successfully
                        Log.d("AlarmUpdate", "АВАРИЯ ДКС: ${objectIdToFind[0]} ID:${objectIdToFind[1]}")
                    } else {
                        // Something went wrong while saving the updated object
                        Log.d("AlarmUpdate", "Error saving: " + saveException.message)
                    }
                }
            } else {
                // Something went wrong
                Log.d("ParseQuery", "Error retrieving object: " + e.message)
            }
        }
    }





    fun findDuplicate(resultAfterScan: String): String {
        val className = "FirstClass"
        var duplicateStatus = "null"
        val query = ParseQuery.getQuery<ParseObject>(className)
        val objectIdToFind = resultAfterScan.split("_").toTypedArray()
        val dksId = objectIdToFind[1].toInt()
        query.whereEqualTo("DKSId", dksId)
        query.getFirstInBackground { parseObject, e ->
            if (parseObject != null) {
                // The element with the specific DKSId exists
                 duplicateStatus = "УЖЕ ЕСТЬ В БАЗЕ!"
                Log.d("MyLog", "QR-код $resultAfterScan УЖЕ ЕСТЬ В БАЗЕ!")
                //todo: сослаться на результат ф-ии Custom Dialog
            } else {
                if (e.code == ParseException.OBJECT_NOT_FOUND) {
                    // The element with the specific DKSId does not exist
                    "Элемент занесён!".also { duplicateStatus = it }
                    Log.d("ParseQuery", duplicateStatus)

                } else {
                    // A different error occurred
                    Log.e("MyLog", "Error: " + e.message)
                }
            }
        }
        return "QR-код $resultAfterScan $duplicateStatus"
    }
//сканирует qr и возвращает номер группы в виде int
fun groupNameFun(resultAfterScan: String): Int {
    val className = "FirstClass"
    val query = ParseQuery.getQuery<ParseObject>(className)
    val objectIdToFind = resultAfterScan.split("_").toTypedArray()
    val supportId = objectIdToFind[2].toInt()
    query.whereEqualTo("SupportId", supportId)
    query.getFirstInBackground { parseObject, e ->
        if (parseObject != null) {
            // The element with the specific DKSId exists
            Log.d("MyLog", "QR-код SupportId $resultAfterScan ЕСТЬ В БАЗЕ!")


        } else {
            if (e.code == ParseException.OBJECT_NOT_FOUND) {
                // The element with the specific DKSId does not exist
                Log.d("ParseQuery", "Элемент занесён!")

            } else {
                // A different error occurred
                Log.e("MyLog", "Error: " + e.message)
            }
        }
    }
    return supportId
}



//@Composable
//fun MinimalDialog(onDismissRequest: () -> Unit) {
//    Dialog(onDismissRequest = { onDismissRequest() }) {
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(200.dp)
//                .padding(16.dp),
//            shape = RoundedCornerShape(16.dp),
//        ) {
//            Text(
//                text = "This is a minimal dialog",
//                modifier = Modifier
//                    .fillMaxSize()
//                    .wrapContentSize(Alignment.Center),
//                textAlign = TextAlign.Center,
//            )
//        }
//    }
//}


//    @Composable
//    fun CustomDialog(
//        resultAfterScan: String,
//
//        ) {
//        Dialog(
//            onDismissRequest = {
//            },
//            properties = DialogProperties(
//                usePlatformDefaultWidth = false
//            )
//        ) {
//            Card(
//                elevation = 5.dp,
//                shape = RoundedCornerShape(15.dp),
//                modifier = Modifier
//                    .fillMaxWidth(0.95f)
//                    .border(2.dp, color = orange, shape = RoundedCornerShape(15.dp))
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(15.dp),
//                    verticalArrangement = Arrangement.spacedBy(25.dp)
//                ) {
//
//                    androidx.compose.material.Text(
//                        text = "$resultAfterScan",
//                        style = MaterialTheme.typography.h6,
//                        textAlign = TextAlign.Center
//                    )
//                    Column(
//                        modifier = Modifier.fillMaxWidth(),
//                        verticalArrangement = Arrangement.spacedBy(15.dp)
//                    ) {
//                        Row(
//                            modifier = Modifier.fillMaxWidth(),
//                            horizontalArrangement = Arrangement.SpaceBetween
//                        ) {
//                            androidx.compose.material.Text("Samsung Galaxy S22")
//                            androidx.compose.material.Text("799,00$")
//                        }
//                        Row(
//                            modifier = Modifier.fillMaxWidth(),
//                            horizontalArrangement = Arrangement.SpaceBetween
//                        ) {
//                            androidx.compose.material.Text("Galaxy S22 Cover Case")
//                            androidx.compose.material.Text("32,00$")
//                        }
//                        Divider()
//
//                        Row(
//                            modifier = Modifier.fillMaxWidth(),
//                            horizontalArrangement = Arrangement.SpaceBetween
//                        ) {
//                            androidx.compose.material.Text("Total", fontWeight = FontWeight.Bold)
//                            androidx.compose.material.Text("831,00$", fontWeight = FontWeight.Bold)
//                        }
//                        Row(
//                            modifier = Modifier.fillMaxWidth(),
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.SpaceAround
//                        ) {
//
//                        }
//
//                    }
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth(),
//                        horizontalArrangement = Arrangement.spacedBy(30.dp),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Button(
//                            onClick = {
//
//                            },
//                            colors = ButtonDefaults.buttonColors(
//                                backgroundColor = orange,
//                                contentColor = white
//                            ),
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .weight(1f),
//                            shape = CircleShape
//                        ) {
//                            androidx.compose.material.Text(
//                                text = "Cancel",
//                                style = MaterialTheme.typography.h6,
//                                fontWeight = FontWeight.Bold,
//                                textAlign = TextAlign.Center,
//                            )
//                        }
//
//                    }
//
//                }
//            }
//        }
//    }

