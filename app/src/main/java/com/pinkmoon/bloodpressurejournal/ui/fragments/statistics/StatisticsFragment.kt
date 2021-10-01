package com.pinkmoon.bloodpressurejournal.ui.fragments.statistics

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker
import com.pinkmoon.bloodpressurejournal.*
import com.pinkmoon.bloodpressurejournal.db.bp_reading.*
import com.pinkmoon.bloodpressurejournal.ui.fragments.dialogs.DisplayBPCatInfoDialogDirections
import java.text.SimpleDateFormat
import java.util.*

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

    // SOURCE: https://www.heart.org/en/health-topics/high-blood-pressure/understanding-blood-pressure-readings
    val BP_RANGES_MAP = hashMapOf(
        (context?.getString(R.string.dialog_bp_ranges_info_normal_title) ?: "Ideal/Normal")
                to (context?.getString(R.string.bp_ranges_info_normal_val) ?: "120/80"),
        (context?.getString(R.string.dialog_bp_ranges_info_elevated_title) ?: "Elevated")
                to (context?.getString(R.string.bp_ranges_info_elevated_val) ?: "129/80"),
        (context?.getString(R.string.dialog_bp_ranges_info_hyp_stage_1_title) ?: "Hypertension Stage 1")
                to (context?.getString(R.string.bp_ranges_info_hyp_stage_1_val) ?: "139/89"),
        (context?.getString(R.string.dialog_bp_ranges_info_hyp_stage_2_title) ?: "Hypertension Stage 2")
                to (context?.getString(R.string.bp_ranges_info_hyp_stage_2_val) ?: "140/90"),
        (context?.getString(R.string.dialog_bp_ranges_info_hyp_crisis_title) ?: "Hypertensive crisis")
                to (context?.getString(R.string.bp_ranges_info_hyp_crisis_val) ?: "180/120")
    )

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
                        displayDataRange(BPDate.getWeekRange())
                    }
                    2 -> {
                        displayDataToday(BPDate.getDateToday())
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

        // titles
        tvTitleIdeal.setOnClickListener {
            findNavController()
                .navigate(getNavActionFromTitle(getString(R.string.frag_stats_title_ideal)))
        }
        tvTitleElevated.setOnClickListener {
            findNavController()
                .navigate(getNavActionFromTitle(getString(R.string.frag_stats_title_elevated)))
        }
        tvTitleHyp1.setOnClickListener {
            findNavController()
                .navigate(getNavActionFromTitle(getString(R.string.frag_stats_title_hyp_stage_1)))
        }
        tvTitleHyp2.setOnClickListener {
            findNavController()
                .navigate(getNavActionFromTitle(getString(R.string.frag_stats_title_hyp_stage_2)))
        }
        tvTitleHypCrisis.setOnClickListener {
            findNavController()
                .navigate(getNavActionFromTitle(getString(R.string.frag_stats_title_hyp_crisis)))
        }

    }

    private fun getNavActionFromTitle(title: String) : NavDirections {
        return StatisticsFragmentDirections
            .actionStatisticsFragmentToDisplayBPCatInfoDialog(title)
    }

    private fun displayDataToday(today: String) {
       bpReadingViewModel.bpReadingsByDate("$today $DAY_TIME_START", "$today $DAY_TIME_END")
           .observe(viewLifecycleOwner, { bpReadings ->
               bpReadings.let {
                   bpReadingAdapter.submitList(bpReadings)
               }
           })
    }

    private fun displayDataRange(weekRangeList: MutableList<String>) {
        //val v = getDateTomorrow
        bpReadingViewModel.bpReadingsByDate(weekRangeList[0], weekRangeList[1])
            .observe(viewLifecycleOwner, { bpReadings ->
                bpReadings.let {
                    bpReadingAdapter.submitList(bpReadings)
                }
            })
    }

    private fun callMDTPDialog() {
        val df = SimpleDateFormat("yyyy-MM-dd")

        // setTheme allows us to make this dialog like, instead of full-screen
        var builder = MaterialDatePicker.Builder.dateRangePicker()
            .setTheme(R.style.ThemeOverlay_MaterialComponents_MaterialCalendar)

        // set default date
        val now = Calendar.getInstance()
        builder.setSelection(Pair(now.timeInMillis, now.timeInMillis))

        // create and call the build method
        val picker = builder.build()
        picker.show(activity?.supportFragmentManager!!, picker.toString())

        // set listeners
        picker.addOnPositiveButtonClickListener {
            val listOfRanges = mutableListOf<String>()
            // DAY_IN_MILLIS is necessary since MTDP seems to like to subtract an extra day to
            // the date range selection: we add an extra day for that reason
            listOfRanges.add("${df.format(it.first + DAY_IN_MILLIS)} $DAY_TIME_START")
            listOfRanges.add("${df.format(it.second + DAY_IN_MILLIS)} $DAY_TIME_END")

            displayDataRange(listOfRanges)
        }

    }
}
