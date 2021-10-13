package com.pinkmoon.bloodpressurejournal.ui.fragments.dialogs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.pinkmoon.bloodpressurejournal.R
import kotlin.ClassCastException

class SelectMedicationNameDialog : DialogFragment(), AdapterView.OnItemSelectedListener {

    // widgets
    private lateinit var spnrMedNames: Spinner
    private lateinit var tvOkay: TextView
    private lateinit var tvCancel: TextView

    // local vars
    private lateinit var listener: SelectMedicationNameDialogListener
    private val args: SelectMedicationNameDialogArgs by navArgs()
    private var selectedMedName = args.medNamesList[0] // default should be the first item in this list

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(
            R.layout.dialog_select_medication_name,
            container,
            false
        )

        defineViews(rootView)
        setOnClickListeners()

        return rootView
    }

    private fun defineViews(view: View) {
        spnrMedNames = view.findViewById(R.id.spnr_dialog_select_medication_name_all_names)
        tvOkay = view.findViewById(R.id.tv_dialog_select_medication_name_okay)
        tvCancel = view.findViewById(R.id.tv_dialog_select_medication_name_cancel)
    }

    private fun setOnClickListeners() {
        spnrMedNames.adapter = context?.let {
            ArrayAdapter(it, android.R.layout.simple_spinner_item, args.medNamesList)
        }

        tvOkay.setOnClickListener {
            val action = SelectMedicationNameDialogDirections
                .actionSelectMedicationNameDialogToNewReminderFragment(selectedMedName)
            findNavController().navigate(action)
        }

        tvCancel.setOnClickListener { dismiss() }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as SelectMedicationNameDialogListener
        } catch (e: ClassCastException){
            throw ClassCastException("$context must implement SelectMedicationNameDialogListener")
        }
    }

    interface SelectMedicationNameDialogListener {
        //fun passSelectedMedicationName(medName: String)
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        selectedMedName = args.medNamesList[p2]
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        selectedMedName = args.medNamesList[0]
    }
}