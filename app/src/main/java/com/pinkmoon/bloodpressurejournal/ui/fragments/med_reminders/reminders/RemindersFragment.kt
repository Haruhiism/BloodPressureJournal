package com.pinkmoon.bloodpressurejournal.ui.fragments.med_reminders.reminders

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pinkmoon.bloodpressurejournal.R
import com.pinkmoon.bloodpressurejournal.ui.fragments.med_reminders.MedicationRemindersFragmentHolderDirections

class RemindersFragment : Fragment(R.layout.fragment_reminders) {

    // widgets
    private lateinit var fabAddNewReminder: FloatingActionButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        defineViews(view)
        setOnClickListeners()
    }

    private fun defineViews(view: View) {
        fabAddNewReminder = view.findViewById(R.id.fab_fragment_reminders_add_new_reminder)
    }

    private fun setOnClickListeners() {
        fabAddNewReminder.setOnClickListener {
            val action = MedicationRemindersFragmentHolderDirections
                .actionMedicationRemindersFragmentHolderToNewReminderFragment()
            findNavController().navigate(action)
        }
    }
}