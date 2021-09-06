package com.pinkmoon.bloodpressurejournal.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pinkmoon.bloodpressurejournal.R

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fab = view.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToSelectNumOfReadingsDialog()
            findNavController().navigate(action)
//            var dialogSelectNumOfReadingsDialog = SelectNumOfReadingsDialog()
//            dialogSelectNumOfReadingsDialog.show(parentFragmentManager, "dialogSelectNumOfReadingsDialog")

//            val intent = Intent(this@MainActivity, NewBPReadingActivity::class.java)
//            startActivityForResult(intent, newBPReadingActivityRequestCode)
        }
    }
}