package com.example.qrcodescannerjp

import android.util.Log
import com.parse.ParseException
import com.parse.ParseObject
import com.parse.ParseQuery

class QueryRetrievers {

    companion object {

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
//функция установки статуса Авария true
        fun findAndChange(resultAfterScan: String) {
            // Replace with your actual class name used in Parse
            val className = "FirstClass"

// Assuming "name" is the field containing the object's name
// and "isAvailable" is the boolean attribute you want to update
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
                            Log.d("AlarmUpdate", "Alarm updated and saved successfully.")
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


    }
}