package com.example.qrcodescannerjp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.parse.ParseObject
import com.parse.ParseQuery

class MainViewModel : ViewModel() {
    private val _supportIdRows = MutableLiveData<List<String>>()
    val supportIdRows: LiveData<List<String>>
        get() = _supportIdRows
//ф-я для каждого id ищет строку собирает её через пробел
    fun fetchRowsBySupportId(supportId: String?) {
        val query = ParseQuery.getQuery<ParseObject>("FirstClass")
        query.whereEqualTo("SupportId", supportId)
        query.findInBackground { objects, e ->
            if (e == null) {
                val rowsAsString = objects.map { parseObject ->
                    // Convert ParseObject to a concatenated string
                    val builder = StringBuilder()
                    parseObject.keySet().forEach { key ->
                        builder.append(parseObject[key].toString()).append(" ")
                    }
                    builder.toString().trim()
                }
                _supportIdRows.postValue(rowsAsString)
            } else {
                // Handle the error, for example, logging or setting an error message
                _supportIdRows.postValue(emptyList())
            }
        }
    }
}