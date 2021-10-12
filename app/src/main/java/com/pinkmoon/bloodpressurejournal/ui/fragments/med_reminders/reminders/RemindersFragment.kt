package com.pinkmoon.bloodpressurejournal.ui.fragments.med_reminders.reminders

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pinkmoon.bloodpressurejournal.BloodPressureJournalApplication
import com.pinkmoon.bloodpressurejournal.R
import com.pinkmoon.bloodpressurejournal.db.reminder.Reminder
import com.pinkmoon.bloodpressurejournal.db.reminder.ReminderListAdapter
import com.pinkmoon.bloodpressurejournal.db.reminder.ReminderViewModel
import com.pinkmoon.bloodpressurejournal.db.reminder.ReminderViewModelFactory
import com.pinkmoon.bloodpressurejournal.ui.fragments.med_reminders.MedicationRemindersFragmentHolderDirections

class RemindersFragment : Fragment(R.layout.fragment_reminders), ReminderListAdapter.OnItemClickListener {

    // local vars
    private val reminderAdapter = ReminderListAdapter(this)
    private val reminderViewModel: ReminderViewModel by viewModels {
        ReminderViewModelFactory(
            (requireActivity().application as BloodPressureJournalApplication).reminderRepository
        )
    }

    // widgets
    private lateinit var fabAddNewReminder: FloatingActionButton
    private lateinit var rvRemindersHolder: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        defineViews(view)
        setOnClickListeners()
        loadRVData()
    }

    private fun defineViews(view: View) {
        fabAddNewReminder = view.findViewById(R.id.fab_fragment_reminders_add_new_reminder)
        rvRemindersHolder = view.findViewById(R.id.rv_fragment_reminders_list_of_reminders)
        rvRemindersHolder.adapter = reminderAdapter
        rvRemindersHolder.layoutManager = LinearLayoutManager(context)
    }

    private fun setOnClickListeners() {
        fabAddNewReminder.setOnClickListener {
            val action = MedicationRemindersFragmentHolderDirections
                .actionMedicationRemindersFragmentHolderToNewReminderFragment()
            findNavController().navigate(action)
        }
    }

    private fun loadRVData() {
        reminderViewModel.allReminders.observe(viewLifecycleOwner, {
            it.let {
                reminderAdapter.submitList(it)
            }
        })
    }

    override fun onItemClick(reminder: Reminder) {

    }
}