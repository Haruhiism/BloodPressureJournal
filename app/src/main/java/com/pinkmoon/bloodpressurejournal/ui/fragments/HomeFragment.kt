package com.pinkmoon.bloodpressurejournal.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.ScatterChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.ScatterData
import com.github.mikephil.charting.data.ScatterDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
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

    private val args: HomeFragmentArgs by navArgs()

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
    }

    override fun onResume() {
        super.onResume()
        if(args.readingsAddedStatus){
            val snack = view?.let {
                Snackbar.make(
                    it,
                    R.string.write_to_db_success,
                    Snackbar.LENGTH_SHORT
                )
            }
            snack?.show()
        }
    }

    private fun defineViews() {
        fabMain = view?.findViewById(R.id.fab_fragment_home_add_reading)!!
        scDailyReadings = view?.findViewById(R.id.sc_fragment_home_daily_readings)!!
        rvLastFiveReadings = view?.findViewById(R.id.rv_fragment_home_last_five_readings)!!

        // general settings for the scatter chart styling
        scDailyReadings.description.isEnabled = false
        scDailyReadings.setPinchZoom(false)
        scDailyReadings.setScaleEnabled(false)
        scDailyReadings.axisLeft.setDrawGridLines(false)
        scDailyReadings.axisRight.setDrawGridLines(false)
        scDailyReadings.xAxis.setDrawGridLines(false)


        rvLastFiveReadings.adapter = bpReadingsAdapter
        rvLastFiveReadings.layoutManager = LinearLayoutManager(context)
    }

    private fun defineObservers() {
        // observers
        bpReadingViewModel.bpReadingsByDate("${getDateToday()} $DAY_TIME_START",
            "${getDateToday()} $DAY_TIME_END")
            .observe(viewLifecycleOwner, {
                bpReading ->
                bpReading.let {
                    bpReadingsByDate = it
                    if(it.isNotEmpty()) bindDBDataToScatterChart()
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

        scDailyReadings.setOnChartValueSelectedListener(object: OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry?, h: Highlight?) {
                displayToastWithEntryDetails(e)
            }

            override fun onNothingSelected() {}
        })
    }

    private fun displayToastWithEntryDetails(e: Entry?) {
        Toast.makeText(
            context,
            "Systolic: ${e?.y?.toInt()} Diastolic: ${e?.x?.toInt()}",
            Toast.LENGTH_LONG
        ).show()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun bindDBDataToScatterChart() {
        // Scatter Chart for daily readings
        var bpReadingEntries = arrayListOf<Entry>()
        for (bpReading in bpReadingsByDate) {
            bpReadingEntries.add(Entry(
                bpReading.diastolicValue.toFloat(),
                bpReading.systolicValue.toFloat(),
                resources.getDrawable(R.drawable.ic_heart_filled_red)
            ))
        }
        bpReadingEntries.sortBy { it.x } //  we need to sort by the x-axis value to prevent crashes

        var bpReadingDataSet = ScatterDataSet(bpReadingEntries, "Single BP Reading")
        bpReadingDataSet.setDrawIcons(true)
        bpReadingDataSet.valueTextSize = 8f
        bpReadingDataSet.color = resources.getColor(R.color.bloodyRed)


        // Scatter Chart for daily reading averages
        var bpReadingsAvgList = bpReadingsByDate.get(0).filterAverages(bpReadingsByDate)
        var bpReadingAvgEntriesList = arrayListOf<Entry>()
        for (bpReadingAvg in bpReadingsAvgList) {
            bpReadingAvgEntriesList.add(Entry(
                bpReadingAvg.diastolicValue.toFloat(),
                bpReadingAvg.systolicValue.toFloat(),
                resources.getDrawable(R.drawable.ic_avg_indicator)
            ))
        }
        bpReadingAvgEntriesList.sortBy { it.x } //  we need to sort by the x-axis value to prevent crashes


        var bpReadingAvgDataSet = ScatterDataSet(bpReadingAvgEntriesList, "Average BP Reading")
        bpReadingAvgDataSet.setDrawIcons(true)
        bpReadingAvgDataSet.valueTextSize = 8f
        bpReadingAvgDataSet.color = resources.getColor(R.color.fadedPurpleBackground)

        var bpReadingAvgScatterDataSet = ScatterData(bpReadingAvgDataSet, bpReadingDataSet)
        scDailyReadings.data = bpReadingAvgScatterDataSet

        scDailyReadings.invalidate()
        scDailyReadings.notifyDataSetChanged()
    }
}
