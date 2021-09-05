package com.pinkmoon.bloodpressurejournal

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pinkmoon.bloodpressurejournal.db.bp_reading.BPReading
import com.pinkmoon.bloodpressurejournal.db.bp_reading.BPReadingListAdapter
import com.pinkmoon.bloodpressurejournal.db.bp_reading.BPReadingViewModel
import com.pinkmoon.bloodpressurejournal.db.bp_reading.BPReadingViewModelFactory

class MainActivity : AppCompatActivity() {

    private val newBPReadingActivityRequestCode = 1

    private val bpReadingViewModel: BPReadingViewModel by viewModels {
        BPReadingViewModelFactory((application as BloodPressureJournalApplication).bpReadingRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = BPReadingListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        bpReadingViewModel.allBpReadings.observe(this, { bpReading ->
            bpReading.let { adapter.submitList(it) }
        })

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewBPReadingActivity::class.java)
            startActivityForResult(intent, newBPReadingActivityRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == newBPReadingActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(NewBPReadingActivity.EXTRA_REPLY)?.let {
                val reading = BPReading(it.toInt(), 74, 69)
                bpReadingViewModel.insert(reading)
            }
        } else {
            Toast.makeText(
                applicationContext,
                "Failed to insert.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}