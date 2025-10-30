package com.example.timestable

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

object DataStore {
    val historyNumbers = mutableListOf<Int>()   // e.g., [3, 5, 8]
}

class MainActivity : AppCompatActivity() {

    private lateinit var etNumber: EditText
    private lateinit var btnGenerate: Button
    private lateinit var btnHistory: Button
    private lateinit var lvResults: ListView

    private val tableItems = arrayListOf<String>()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etNumber   = findViewById(R.id.etNumber)
        btnGenerate = findViewById(R.id.btnGenerate)
        btnHistory  = findViewById(R.id.btnHistory)
        lvResults   = findViewById(R.id.lvResults)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tableItems)
        lvResults.adapter = adapter


        btnGenerate.setOnClickListener {
            val txt = etNumber.text.toString().trim()
            if (txt.isEmpty()) {
                Toast.makeText(this, "Please enter a number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val n = txt.toInt()
            tableItems.clear()
            for (i in 1..10) {
                tableItems.add("$n × $i = ${n * i}")
            }
            adapter.notifyDataSetChanged()

            if (!DataStore.historyNumbers.contains(n)) {
                DataStore.historyNumbers.add(n)
            }
        }

        lvResults.setOnItemClickListener { _, _, position, _ ->
            val item = tableItems[position]
            AlertDialog.Builder(this)
                .setTitle("Delete row?")
                .setMessage("Delete \"$item\"?")
                .setPositiveButton("Delete") { _, _ ->
                    tableItems.removeAt(position)
                    adapter.notifyDataSetChanged()
                    Toast.makeText(this, "Deleted: $item", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        btnHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
    }
    
    override fun onCreateOptionsMenu(menu: android.view.Menu): Boolean {
        menu.add(0, 1, 0, "History")
        return true
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        if (item.itemId == 1) {
            startActivity(Intent(this, HistoryActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
