package com.pinkmoon.bloodpressurejournal.ui.fragments.med_reminders.medications

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pinkmoon.bloodpressurejournal.R
import com.pinkmoon.bloodpressurejournal.ui.fragments.med_reminders.MedicationRemindersFragmentHolderDirections

class MedicationsFragment : Fragment(R.layout.fragment_medications) {

    // widgets
    private lateinit var fabAddNewMedication: FloatingActionButton

    // local vars

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        defineViews(view)
        setOnClickListeners()
    }

    private fun defineViews(view: View) {
        fabAddNewMedication = view.findViewById(R.id.fab_fragment_medications_add_new_medication)
    }

    private fun setOnClickListeners() {
        // Main FAB
        fabAddNewMedication.setOnClickListener {
            val action = MedicationRemindersFragmentHolderDirections
                .actionMedicationRemindersFragmentHolderToNewMedicationFragment()
            findNavController().navigate(action)
        }
    }
}
