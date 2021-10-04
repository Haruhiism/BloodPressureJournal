package com.pinkmoon.bloodpressurejournal.ui.fragments.med_reminders.medications

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
import com.pinkmoon.bloodpressurejournal.db.medication.Medication
import com.pinkmoon.bloodpressurejournal.db.medication.MedicationListAdapter
import com.pinkmoon.bloodpressurejournal.db.medication.MedicationViewModel
import com.pinkmoon.bloodpressurejournal.db.medication.MedicationViewModelFactory
import com.pinkmoon.bloodpressurejournal.ui.fragments.med_reminders.MedicationRemindersFragmentHolderDirections

class MedicationsFragment : Fragment(R.layout.fragment_medications), MedicationListAdapter.OnItemClickListener {

    // widgets
    private lateinit var fabAddNewMedication: FloatingActionButton
    private lateinit var rvMedicationList: RecyclerView

    // local vars
    private var medicationAdapter = MedicationListAdapter(this)
    private val medicationViewModel: MedicationViewModel by viewModels {
        MedicationViewModelFactory(
            (requireActivity().application as BloodPressureJournalApplication).medicationRepository
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        defineViews(view)
        loadRvData()
        setOnClickListeners()
    }

    private fun defineViews(view: View) {
        fabAddNewMedication = view.findViewById(R.id.fab_fragment_medications_add_new_medication)
        rvMedicationList = view.findViewById(R.id.rv_fragment_reminders_list_of_medications)
        rvMedicationList.adapter = medicationAdapter
        rvMedicationList.layoutManager = LinearLayoutManager(context)
    }

    private fun loadRvData() {
        medicationViewModel.allMedications.observe(viewLifecycleOwner, {
            it.let {
                medicationAdapter.submitList(it)
            }
        })
    }

    private fun setOnClickListeners() {
        // Main FAB
        fabAddNewMedication.setOnClickListener {
            val action = MedicationRemindersFragmentHolderDirections
                .actionMedicationRemindersFragmentHolderToNewMedicationFragment()
            findNavController().navigate(action)
        }
    }

    override fun onItemClick(medication: Medication) {
        // if the user clicks this, we edit the Medication
        val action = MedicationRemindersFragmentHolderDirections
            .actionMedicationRemindersFragmentHolderToNewMedicationFragment(medication)
        findNavController().navigate(action)
    }
}
