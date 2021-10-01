package com.pinkmoon.bloodpressurejournal.ui.fragments.dialogs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.pinkmoon.bloodpressurejournal.R
import es.dmoral.toasty.Toasty
import kotlin.ClassCastException

class SelectNumOfReadingsDialog : DialogFragment() {

    lateinit var mListener: SelectNumOfReadingsDialogListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView: View = inflater.inflate(
            R.layout.dialog_select_num_of_readings,
            container,
            false)

        val tvCancel = rootView.findViewById<TextView>(R.id.tv_dialog_select_num_of_readings_cancel)
        val tvOkay = rootView.findViewById<TextView>(R.id.tv_dialog_select_num_of_readings_okay)


        tvCancel.setOnClickListener {
            dismiss()
        }

        tvOkay.setOnClickListener {
            val rgRadioGroup = rootView.findViewById<RadioGroup>(R.id.rg_dialog_select_num_of_holder)
            val selectedId = rgRadioGroup.checkedRadioButtonId
            // if the user selects nothing, but clicks 'Okay' anyway, we may have a null here
            val radio: RadioButton? = rootView.findViewById(selectedId)

            when (radio?.id) {
                R.id.rb_dialog_select_num_of_holder_single_reading -> {
                    val action = SelectNumOfReadingsDialogDirections
                        .actionSelectNumOfReadingsDialogToBPReadingHolderFragment(1)
                    findNavController().navigate(action)
                }
                R.id.rb_dialog_select_num_of_holder_three_readings -> {
                    val action = SelectNumOfReadingsDialogDirections
                        .actionSelectNumOfReadingsDialogToBPReadingHolderFragment(3)
                    findNavController().navigate(action)
                }
                else -> {
                    context?.let { con ->
                        Toasty.warning(
                            con,
                            getString(R.string.dialog_select_num_of_readings_none_selected),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    // ensure we don't leave the dialog until the user either selects something,
                    // or taps on cancel out of the dialog
                    return@setOnClickListener
                }
            }
            dismiss()
        }

        return rootView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mListener = context as SelectNumOfReadingsDialogListener
        } catch (e: ClassCastException){
            throw ClassCastException("$context must implement SelectNumOfReadingsDialogListener")
        }
    }

    interface SelectNumOfReadingsDialogListener {
        fun passUserReadingSelection(numOfReadings: Int)
    }
}