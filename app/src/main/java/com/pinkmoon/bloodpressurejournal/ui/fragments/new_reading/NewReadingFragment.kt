package com.pinkmoon.bloodpressurejournal.ui.fragments.new_reading

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pinkmoon.bloodpressurejournal.R
import com.pinkmoon.bloodpressurejournal.db.bp_reading.BPReading
import com.shawnlin.numberpicker.NumberPicker


class NewReadingFragment : Fragment(R.layout.fragment_new_reading) {

    // widgets
    private var npSystolicVal: NumberPicker? = null
    private var npDiastolicVal: NumberPicker? = null
    private var npPulseVal: NumberPicker? = null

    // local vars
    private lateinit var newReadingFragmentViewModel: NewReadingFragmentViewModel
    private var selectedSysVal: Int = 120
    private var selectedDiasVal: Int = 80
    private var selectedPulseVal: Int = 72

    private var bpReadingObj = BPReading()

    lateinit var callback: OnNewReadingFragmentListener

    lateinit var fragmentTitle: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentTitle = arguments?.getString("KEY") ?: "Could not get title."
        defineViews(view)
        defineObservers()
        setOnClickListeners()
        // pass back a default BPReading object in case the user leaves some values untouched
        callback.passBPReadingObj(bpReadingObj, fragmentTitle)
    }

    private fun defineViews(view: View) {
        newReadingFragmentViewModel = ViewModelProvider(this).get(NewReadingFragmentViewModel::class.java)

        npSystolicVal = view.findViewById(R.id.np_fragment_add_reading_systolic_input)
        npDiastolicVal = view.findViewById(R.id.np_fragment_add_reading_diastolic_input)
        npPulseVal = view.findViewById(R.id.np_fragment_add_reading_pulse_input)
    }

    private fun defineObservers() {
        newReadingFragmentViewModel.currentSystolicValue.observe(viewLifecycleOwner, Observer {
                systolicValFromObserver ->
            npSystolicVal?.let {
                it.value = systolicValFromObserver.toInt()
                setSelectedSystolicVal(it.value)
            }
        })

        newReadingFragmentViewModel.currentDiastolicValue.observe(viewLifecycleOwner, Observer {
                diastolicValFromObserver ->

            npDiastolicVal?.let {
                it.value = diastolicValFromObserver.toInt()
                setSelectedDiastolicVal(it.value)
            }
        })

        newReadingFragmentViewModel.currentPulseValue.observe(viewLifecycleOwner, Observer {
                pulseValFromObserver ->

            npPulseVal?.let {
                it.value = pulseValFromObserver.toInt()
                setSelectedPulseVal(it.value)
            }
        })
    }

    private fun setOnClickListeners() {

        npSystolicVal?.setOnValueChangedListener { _, _, newVal ->
            setSelectedSystolicVal(newVal)
            newReadingFragmentViewModel.currentSystolicValue.value = newVal
            recreateBPReadingObj(selectedSysVal, selectedDiasVal, selectedPulseVal)
            callback.passBPReadingObj(bpReadingObj, fragmentTitle)
        }

        npDiastolicVal?.setOnValueChangedListener { _, _, newVal ->
            setSelectedDiastolicVal(newVal)
            newReadingFragmentViewModel.currentDiastolicValue.value = newVal
            recreateBPReadingObj(selectedSysVal, selectedDiasVal, selectedPulseVal)
            callback.passBPReadingObj(bpReadingObj, fragmentTitle)
        }

        npPulseVal?.setOnValueChangedListener { _, _, newVal ->
            setSelectedPulseVal(newVal)
            newReadingFragmentViewModel.currentPulseValue.value = newVal
            recreateBPReadingObj(selectedSysVal, selectedDiasVal, selectedPulseVal)
            callback.passBPReadingObj(bpReadingObj, fragmentTitle)
        }
    }

    private fun setSelectedSystolicVal(newVal: Int) { selectedSysVal = newVal }

    private fun setSelectedDiastolicVal(newVal: Int) { selectedDiasVal = newVal }

    private fun setSelectedPulseVal(newVal: Int) { selectedPulseVal = newVal }

    private fun recreateBPReadingObj(systolicVal: Int, diastolicVal: Int, pulseVal: Int) {
        bpReadingObj.systolicValue = systolicVal
        bpReadingObj.diastolicValue = diastolicVal
        bpReadingObj.pulseValue = pulseVal
    }

    fun setOnNewReadingFragmentListener(callback: OnNewReadingFragmentListener) {
        this.callback = callback
    }

    interface OnNewReadingFragmentListener {
        fun passBPReadingObj(bpReading: BPReading, fragmentTitle: String)
    }
}
