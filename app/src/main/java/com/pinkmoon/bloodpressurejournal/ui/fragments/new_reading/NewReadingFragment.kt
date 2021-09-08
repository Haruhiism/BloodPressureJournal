package com.pinkmoon.bloodpressurejournal.ui.fragments.new_reading

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.pinkmoon.bloodpressurejournal.R
import com.shawnlin.numberpicker.NumberPicker

class NewReadingFragment : Fragment(R.layout.fragment_new_reading) {

    // widgets
    private var npSystolicVal: NumberPicker? = null
    private var npDiastolicVal: NumberPicker? = null
    private var npPulseVal: NumberPicker? = null

    // local vars
    private lateinit var newReadingFragmentViewModel: NewReadingFragmentViewModel
    private var selectedSysVal: Int = 0
    private var selectedDiasVal: Int = 0
    private var selectedPulseVal: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        defineViews(view)
        defineObservers()
        setOnClickListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_readings_options, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save -> {
                val snack = view?.let { Snackbar.make(it, "Saved.", Snackbar.LENGTH_SHORT) }
                snack?.show()
                true
            }
            else -> {
                false
            }
        }
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
        }

        npDiastolicVal?.setOnValueChangedListener { _, _, newVal ->
            setSelectedDiastolicVal(newVal)
            newReadingFragmentViewModel.currentDiastolicValue.value = newVal
        }

        npPulseVal?.setOnValueChangedListener { _, _, newVal ->
            setSelectedPulseVal(newVal)
            newReadingFragmentViewModel.currentPulseValue.value = newVal
        }
    }

    private fun setSelectedSystolicVal(newVal: Int) { selectedSysVal = newVal }

    private fun setSelectedDiastolicVal(newVal: Int) { selectedDiasVal = newVal }

    private fun setSelectedPulseVal(newVal: Int) { selectedPulseVal = newVal }
}