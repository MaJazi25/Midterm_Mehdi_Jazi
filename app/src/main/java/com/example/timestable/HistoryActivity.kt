package com.example.timestable

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val listView = ListView(this)
        setContentView(listView)

        val numbersAsText = DataStore.historyNumbers.map { it.toString() }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, numbersAsText)
        listView.adapter = adapter
        title = "History"
    }
}
