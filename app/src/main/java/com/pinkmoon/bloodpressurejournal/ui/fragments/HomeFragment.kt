package com.pinkmoon.bloodpressurejournal.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.ScatterChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.ScatterData
import com.github.mikephil.charting.data.ScatterDataSet
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pinkmoon.bloodpressurejournal.BloodPressureJournalApplication
import com.pinkmoon.bloodpressurejournal.R
import com.pinkmoon.bloodpressurejournal.db.bp_reading.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    // local vars
    private val bpReadingViewModel: BPReadingViewModel by viewModels {
        BPReadingViewModelFactory(
            (requireActivity().application as BloodPressureJournalApplication).bpReadingRepository
        )
    }

    private lateinit var bpReadingsByDate: List<BPReading>
    private var bpReadingsAdapter = BPReadingListAdapter()

    // widgets
    lateinit var fabMain: FloatingActionButton
    private lateinit var scDailyReadings: ScatterChart
    private lateinit var rvLastFiveReadings: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        defineViews()
        defineObservers()
        setListeners()
        //bindDBDataToViews()
    }

    private fun defineViews() {
        fabMain = view?.findViewById(R.id.fab_fragment_home_add_reading)!!
        scDailyReadings = view?.findViewById(R.id.sc_fragment_home_daily_readings)!!
        rvLastFiveReadings = view?.findViewById(R.id.rv_fragment_home_last_five_readings)!!

        // general settings for the scatter chart styling
        scDailyReadings.description.isEnabled = false

        rvLastFiveReadings.adapter = bpReadingsAdapter
        rvLastFiveReadings.layoutManager = LinearLayoutManager(context)
    }

    private fun defineObservers() {
        // observers
        bpReadingViewModel.bpReadingsByDate(getDateStartToday, getDateEndToday).observe(viewLifecycleOwner, {
                bpReading ->
            bpReading.let {
                bpReadingsByDate = it
                bindDBDataToViews()
            }
        })

        bpReadingViewModel.lastFiveReadings.observe(viewLifecycleOwner, { bpReadings ->
            bpReadings.let {
                bpReadingsAdapter.submitList(bpReadings)
            }
        })
    }

    private fun setListeners() {
        fabMain.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToSelectNumOfReadingsDialog()
            findNavController().navigate(action)
        }
    }

    private fun bindDBDataToViews() {
        // Scatter Chart
        var bpReadingEntries = arrayListOf<Entry>()
        for (bpReading in bpReadingsByDate) {
            bpReadingEntries.add(Entry(
                bpReading.diastolicValue.toFloat(),
                bpReading.systolicValue.toFloat()
            ))
        }

        var bpReadingDataSet = ScatterDataSet(bpReadingEntries, "BP Reading")
        bpReadingDataSet.color = resources.getColor(R.color.bloodyRed)

        var bpReadingScatterDataSet = ScatterData(bpReadingDataSet)
        scDailyReadings.data = bpReadingScatterDataSet
        scDailyReadings.invalidate()
        scDailyReadings.notifyDataSetChanged()
    }
}