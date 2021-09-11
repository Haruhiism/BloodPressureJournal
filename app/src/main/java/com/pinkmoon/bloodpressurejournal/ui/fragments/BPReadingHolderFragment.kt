package com.pinkmoon.bloodpressurejournal.ui.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.pinkmoon.bloodpressurejournal.BloodPressureJournalApplication
import com.pinkmoon.bloodpressurejournal.R
import com.pinkmoon.bloodpressurejournal.db.bp_reading.*
import com.pinkmoon.bloodpressurejournal.ui.fragments.new_reading.NewReadingFragment

class BPReadingHolderFragment : Fragment(R.layout.fragment_bp_reading_holder),
    NewReadingFragment.OnNewReadingFragmentListener {

    private val args: BPReadingHolderFragmentArgs by navArgs()

    private var bpReadingMap = hashMapOf<String, BPReading>()

    private val bpReadingViewModel: BPReadingViewModel by viewModels {
        BPReadingViewModelFactory((requireActivity().application as BloodPressureJournalApplication).bpReadingRepository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Create the object of Toolbar, ViewPager and
        // TabLayout and use “findViewById()” method*/
//        var tab_toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        var tab_viewpager = view.findViewById<ViewPager>(R.id.tab_viewpager)
        var tab_tablayout = view.findViewById<TabLayout>(R.id.tab_tablayout)

        setHasOptionsMenu(true)

        setupViewPager(tab_viewpager)
        tab_tablayout.setupWithViewPager(tab_viewpager)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_readings_options, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save -> {
                logBPReadingsToDB()
                takeUserBackToMain()
                true
            }
            else -> {
                false
            }
        }
    }

    private fun setupViewPager(viewpager: ViewPager) {
        var adapter = ViewPageAdapter(childFragmentManager) // maybe child instead?

        if(args.numberOfReadings == 1) {
            adapter.addFragment(NewReadingFragment(), "Reading 1")
        } else if (args.numberOfReadings == 3) {

            adapter.addFragment(NewReadingFragment(), "Reading 1")
            adapter.addFragment(NewReadingFragment(), "Reading 2")
            adapter.addFragment(NewReadingFragment(), "Reading 3")
        }

        viewpager.adapter = adapter
    }

    class ViewPageAdapter : FragmentPagerAdapter {
        private var fragmentList1: ArrayList<Fragment> = ArrayList()

        private var fragmentTitleList1: ArrayList<String> = ArrayList()

        constructor(supportFragmentManager: FragmentManager)
                : super(supportFragmentManager)

        override fun getCount(): Int {
            return fragmentList1.size
        }

        override fun getItem(position: Int): Fragment {
            return fragmentList1.get(position)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return fragmentTitleList1.get(position)
        }

        fun addFragment(fragment: Fragment, title: String) {
            //fragment.arguments?.putString(NewReadingFragment.FRAGMENT_TITLE_KEY, title)
            val bundle = Bundle()
            bundle.putString("KEY", title)
            fragment.arguments = bundle
            fragmentList1.add(fragment)
            fragmentTitleList1.add(title)
        }
    }

    override fun onAttachFragment(fragment: Fragment) {
        if(fragment is NewReadingFragment) {
            fragment.setOnNewReadingFragmentListener(this)
        }
    }

    private fun logBPReadingsToDB() {
        if (bpReadingMap.isNotEmpty()) {
            for (reading in bpReadingMap) {
                bpReadingViewModel.insert(reading.value)
            }
        } else {
            bpReadingViewModel.insert(BPReading()) // insert a single default reading
        }
    }

    private fun takeUserBackToMain() {
        val action = BPReadingHolderFragmentDirections
            .actionBPReadingHolderFragmentToHomeFragment(true)
        findNavController().navigate(action)
    }

    override fun passBPReadingObj(bpReading: BPReading, fragmentTitle: String) {
        bpReadingMap[fragmentTitle] = bpReading

//        var snack = view?.let {
//            Snackbar.make(
//                it,
//                "Systolic: ${bpReading.systolicValue}, " +
//                        "Diastolic: ${bpReading.diastolicValue}, " +
//                        "Pulse: ${bpReading.pulseValue} " +
//                        "from $fragmentTitle",
//                Snackbar.LENGTH_LONG)
//        }
//        snack?.show()
    }
}