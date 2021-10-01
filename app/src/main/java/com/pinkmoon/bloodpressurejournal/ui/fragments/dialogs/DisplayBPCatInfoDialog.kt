package com.pinkmoon.bloodpressurejournal.ui.fragments.dialogs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.pinkmoon.bloodpressurejournal.R

class DisplayBPCatInfoDialog : DialogFragment() {

    // widgets
    private lateinit var tvHeader: TextView
    private lateinit var tvBody: TextView
    private lateinit var tvOkay: TextView

    // local vars
    private val args: DisplayBPCatInfoDialogArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(
            R.layout.dialog_display_bp_cat_info,
            container,
            false)

        defineViews(rootView)
        setViewData()
        setOnClickListeners()

        return rootView
    }

    private fun defineViews(rootView: View) {
        tvHeader = rootView.findViewById(R.id.tv_dialog_display_bp_cat_info_header)
        tvBody = rootView.findViewById(R.id.tv_dialog_display_bp_cat_info_body)
        tvOkay = rootView.findViewById(R.id.tv_dialog_display_bp_cat_info_okay)
    }

    private fun setViewData() {
        tvHeader.text = args.selectedBPCatInfoTitle
        when(args.selectedBPCatInfoTitle) {
            getString(R.string.frag_stats_title_ideal) -> {
                tvBody.text = getString(R.string.dialog_bp_ranges_info_normal)
            }
            getString(R.string.frag_stats_title_elevated) -> {
                tvBody.text = getString(R.string.dialog_bp_ranges_info_elevated)
            }
            getString(R.string.frag_stats_title_hyp_stage_1) -> {
                tvBody.text = getString(R.string.dialog_bp_ranges_info_hyp_stage_1)
            }
            getString(R.string.frag_stats_title_hyp_stage_2) -> {
                tvBody.text = getString(R.string.dialog_bp_ranges_info_hyp_stage_2)
            }
            getString(R.string.frag_stats_title_hyp_crisis) -> {
                tvBody.text = getString(R.string.dialog_bp_ranges_info_hyp_crisis)
            }
        }
    }

    private fun setOnClickListeners() {
        tvOkay.setOnClickListener { dismiss() }
    }

}