package com.pinkmoon.bloodpressurejournal.ui.fragments.med_reminders.reminders

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pinkmoon.bloodpressurejournal.BloodPressureJournalApplication
import com.pinkmoon.bloodpressurejournal.R
import com.pinkmoon.bloodpressurejournal.db.join_redication_reminder.JoinMedicationReminder
import com.pinkmoon.bloodpressurejournal.db.join_redication_reminder.JoinMedicationReminderListAdapter
import com.pinkmoon.bloodpressurejournal.db.join_redication_reminder.JoinMedicationReminderViewModel
import com.pinkmoon.bloodpressurejournal.db.join_redication_reminder.JoinMedicationReminderViewModelFactory
import com.pinkmoon.bloodpressurejournal.db.reminder.Reminder
import com.pinkmoon.bloodpressurejournal.db.reminder.ReminderListAdapter
import com.pinkmoon.bloodpressurejournal.db.reminder.ReminderViewModel
import com.pinkmoon.bloodpressurejournal.db.reminder.ReminderViewModelFactory

import com.pinkmoon.bloodpressurejournal.ui.fragments.med_reminders.MedicationRemindersFragmentHolderDirections
import es.dmoral.toasty.Toasty

class RemindersFragment : Fragment(R.layout.fragment_reminders),
    ReminderListAdapter.OnItemClickListener,
    JoinMedicationReminderListAdapter.OnItemClickListener {

    // local vars
    private val reminderAdapter = ReminderListAdapter(this)
    private val reminderViewModel: ReminderViewModel by viewModels {
        ReminderViewModelFactory(
            (requireActivity().application as BloodPressureJournalApplication).reminderRepository
        )
    }
    private val joinMedicationReminderViewModel: JoinMedicationReminderViewModel by viewModels {
        JoinMedicationReminderViewModelFactory(
            (requireActivity().application as BloodPressureJournalApplication).joinMedicationReminderRepository
        )
    }
    private val medNamesList = arrayListOf<String>()
    private val joinMedRemListAdapter = JoinMedicationReminderListAdapter(this)

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
        rvRemindersHolder.adapter = joinMedRemListAdapter
        rvRemindersHolder.layoutManager = LinearLayoutManager(context)
    }

    private fun setOnClickListeners() {
        fabAddNewReminder.setOnClickListener {
            val action = MedicationRemindersFragmentHolderDirections
                .actionMedicationRemindersFragmentHolderToSelectMedicationNameDialog(medNamesList.toTypedArray())
            findNavController().navigate(action)
        }
    }

    private fun loadRVData() {
        joinMedicationReminderViewModel.allJoinMedicationReminders.observe(viewLifecycleOwner, {
            // fill the list of names we are to pass as args to the NewReminder fragment
            // also submit the list of to the adapter with non-null items
            medNamesList.clear()
            for (medReminder in it){
                medNamesList.add(medReminder.name)
                val newMedRemList = arrayListOf<JoinMedicationReminder>()
                if(medReminder.scheduledTime != null){
                    newMedRemList.add(medReminder)
                }
                joinMedRemListAdapter.submitList(newMedRemList)
            }
        })
    }

    override fun onItemClick(reminder: Reminder) {
        context.let {
            if (it != null) {
                Toasty.normal(it, reminder.scheduledTime, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onItemClick(medicationReminder: JoinMedicationReminder) {
        context.let {
            if (it != null) {
                Toasty.normal(it, medicationReminder.name, Toast.LENGTH_SHORT).show()
            }
        }
    }
}