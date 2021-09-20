package com.pinkmoon.bloodpressurejournal.ui.fragments.statistics

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pinkmoon.bloodpressurejournal.BloodPressureJournalApplication
import com.pinkmoon.bloodpressurejournal.R
import com.pinkmoon.bloodpressurejournal.db.bp_reading.*

class StatisticsFragment : Fragment(R.layout.fragment_statistics) {

    // widgets
    private lateinit var spnrDateRange: Spinner

    // the indicators of the range each BP reading falls under
    private lateinit var tvTitleIdeal: TextView
    private lateinit var tvTitleElevated: TextView
    private lateinit var tvTitleHyp1: TextView
    private lateinit var tvTitleHyp2: TextView
    private lateinit var tvTitleHypCrisis: TextView

    private lateinit var rvBPReadings: RecyclerView

    // local var
    private lateinit var statisticsFragmentViewModel: StatisticsFragmentViewModel
    private var selectedSpinnerField: Int = 0 // 0 = select a range to display (default)

    private var bpReadingAdapter = BPReadingListAdapter()

    private lateinit var startDate: String
    private lateinit var endDate: String

    private val bpReadingViewModel: BPReadingViewModel by viewModels {
        BPReadingViewModelFactory(
            (requireActivity().application as BloodPressureJournalApplication).bpReadingRepository
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true) // allows us to customize the existing menu a bit within this frag

        defineViews(view)
        defineObservers()
        setOnClickListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_statistics_options, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        return when(item.itemId) {
            R.id.export -> {
                exportData()
                true
            } else -> {
                false
            }
        }
    }

    /**
     * Exports the selected range data into an excel or pdf file
     */
    private fun exportData(){
        Toast.makeText(
            context,
            "Selected Spinner val: $selectedSpinnerField",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun defineViews(view: View) {
        spnrDateRange = view.findViewById(R.id.spnr_fragment_statistics_main_range_shown)

        tvTitleIdeal = view.findViewById(R.id.tv_fragment_statistics_main_ideal_header)
        tvTitleElevated = view.findViewById(R.id.tv_fragment_statistics_main_elevated_header)
        tvTitleHyp1 = view.findViewById(R.id.tv_fragment_statistics_main_hyp_1_header)
        tvTitleHyp2 = view.findViewById(R.id.tv_fragment_statistics_main_hyp_2_header)
        tvTitleHypCrisis = view.findViewById(R.id.tv_fragment_statistics_main_hyp_crisis_header)

        rvBPReadings = view.findViewById(R.id.rv_fragment_statistics_readings)
        rvBPReadings.adapter = bpReadingAdapter
        rvBPReadings.layoutManager = LinearLayoutManager(context)

        statisticsFragmentViewModel = ViewModelProvider(this)
            .get(StatisticsFragmentViewModel::class.java)
    }

    // TODO may not be necessary at this point
    private fun defineObservers() {
        //displayDataRange(BPReading().getWeekRange())
//        bpReadingViewModel.allBpReadings.observe(viewLifecycleOwner, { bpReadings ->
//            bpReadings.let {
//                bpReadingAdapter.submitList(bpReadings)
//            }
//        })
//        statisticsFragmentViewModel.currentSelectedSpinnerField.observe(viewLifecycleOwner, Observer {
//            selectedSpinnerFieldFromObserver ->
//            run {
//                spnrDateRange.setSelection(selectedSpinnerFieldFromObserver.toInt())
//                selectedSpinnerField = selectedSpinnerFieldFromObserver.toInt()
//            }
//        })
//
//        statisticsFragmentViewModel.currentStartDate.observe(viewLifecycleOwner, Observer {
//            selectedStartDateFromObserver ->
//            run {
//                startDate = selectedStartDateFromObserver.toString()
//            }
//        })
    }

    private fun setOnClickListeners() {
        spnrDateRange.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedSpinnerField = p2
                when(p0!!.selectedItemPosition){
                    0 -> {
                        // Do nothing
                    }
                    1 -> {
                        displayDataRange(BPReading().getWeekRange())
                    }
                    2 -> {
                        displayDataToday(getDateToday)
                    }
                    3 -> {
                        callMDTPDialog()
                    }
                    else -> {
                        // Do nothing
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }

    private fun displayDataToday(today: String) {
       // bpReadingViewModel.
    }

    private fun displayDataRange(weekRangeList: MutableList<String>) {
        val v = getDateTomorrow
        bpReadingViewModel.bpReadingsByDate(weekRangeList[0], weekRangeList[1])
            .observe(viewLifecycleOwner, { bpReadings ->
                bpReadings.let {
                    bpReadingAdapter.submitList(bpReadings)
                }
            })
    }

    private fun callMDTPDialog() {

    }
}
