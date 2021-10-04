package com.pinkmoon.bloodpressurejournal.ui.fragments.med_reminders.medications

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.pinkmoon.bloodpressurejournal.BloodPressureJournalApplication
import com.pinkmoon.bloodpressurejournal.R
import com.pinkmoon.bloodpressurejournal.db.medication.Medication
import com.pinkmoon.bloodpressurejournal.db.medication.MedicationViewModel
import com.pinkmoon.bloodpressurejournal.db.medication.MedicationViewModelFactory
import es.dmoral.toasty.Toasty

class NewMedicationFragment : Fragment(R.layout.fragment_new_medication) {

    // widgets
    private lateinit var etMedicationName: TextInputLayout
    private lateinit var etMedicationDosage: TextInputLayout
    private lateinit var etMedicationQuantity: TextInputLayout

    // local vars
    private var medicationName = ""
    private var dosage = 0.0
    private var quantity = 0

    private var medicationEdit: Medication? = null

    private val args: NewMedicationFragmentArgs by navArgs()

    private val medicationViewModel: MedicationViewModel by viewModels {
        MedicationViewModelFactory((requireActivity().application as BloodPressureJournalApplication).medicationRepository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        defineViews(view)
        checkIfEdit()
        setOnClickListeners()

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_readings_options, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save -> {
                saveNewMedication()
                true
            }
            else -> {
                false
            }
        }
    }

    private fun defineViews(view: View) {
        etMedicationName = view.findViewById(R.id.et_fragment_new_medication_name)
        etMedicationDosage = view.findViewById(R.id.et_fragment_new_medication_dosage)
        etMedicationQuantity = view.findViewById(R.id.et_fragment_new_medication_quantity)
    }

    /**
     * Checks if an object of type Medication was passed via safeargs, and
     * if so, it assigns it to the global variable medicationEdit.
     */
    private fun checkIfEdit() {
        medicationEdit = args.medication?.also {
            etMedicationName.editText?.setText(it.name)
            etMedicationDosage.editText?.setText(it.dosage.toString())
            etMedicationQuantity.editText?.setText(it.quantity.toString())

            medicationName = it.name
            dosage = it.dosage
            quantity = it.quantity
        }
    }

    private fun setOnClickListeners() {
        etMedicationName.editText?.doOnTextChanged { text, _, _, _ ->
            medicationName = text.toString().trim()
        }

        etMedicationDosage.editText?.doOnTextChanged { text, _, _, _ ->
            dosage = text.toString().toDouble()
        }

        etMedicationQuantity.editText?.doOnTextChanged { text, _, _, _ ->
            quantity = text.toString().toInt()
        }
    }

    private fun saveNewMedication() {
        //context?.let { Toasty.info(it, "Medication saved.", Toast.LENGTH_LONG).show() }

        if(verifyIntegrityOfInputs()) {
            if(medicationEdit != null) {
                val medication = Medication(medicationName, dosage, quantity)
                medication.pId = medicationEdit!!.pId
                medicationViewModel.updateMedication(medication)
            } else {
                medicationViewModel.insertMedication(Medication(medicationName, dosage, quantity))
            }
            takeUserBackToMain(true)
        }else {
            context?.let {
                Toasty.error(
                    it,
                    getString(R.string.frag_new_medication_blank_error),
                    Toast.LENGTH_SHORT
                ).show()
            }
            return
        }
    }

    private fun verifyIntegrityOfInputs() : Boolean{
        return medicationName.isNotBlank() && dosage != 0.0 && quantity != 0
    }

    private fun takeUserBackToMain(medicationAdded: Boolean) {
        val action = NewMedicationFragmentDirections
            .actionNewMedicationFragmentToMedicationRemindersFragmentHolder(medicationAdded)
        findNavController().navigate(action)
    }
}
