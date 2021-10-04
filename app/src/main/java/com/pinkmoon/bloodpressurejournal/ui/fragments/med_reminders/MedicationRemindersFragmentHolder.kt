package com.pinkmoon.bloodpressurejournal.ui.fragments.med_reminders

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.pinkmoon.bloodpressurejournal.R
import com.pinkmoon.bloodpressurejournal.ui.fragments.med_reminders.medications.MedicationsFragment
import com.pinkmoon.bloodpressurejournal.ui.fragments.med_reminders.reminders.RemindersFragment

class MedicationRemindersFragmentHolder : Fragment(R.layout.fragment_medication_reminders_holder) {

    // local vars
    val args: MedicationRemindersFragmentHolderArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var tvpMainViewPager = view.findViewById<ViewPager>(R.id.tvp_fragment_medication_reminders_holder_pager)
        var tlMainTabLayout = view.findViewById<TabLayout>(R.id.tl_fragment_medication_reminders_holder_main_holder)

        setHasOptionsMenu(true)

        setUpViewPager(tvpMainViewPager)
        tlMainTabLayout.setupWithViewPager(tvpMainViewPager)
    }

    override fun onResume() {
        super.onResume()
        if(args.medicationAdded){
            view?.let {
                Snackbar.make(
                    it,
                    getString(R.string.frag_new_medication_saved),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setUpViewPager(tvpMainViewPager: ViewPager?) {
        var adapter = ViewPageAdapter(childFragmentManager)

        adapter.addFragment(MedicationsFragment(), "Medications")
        adapter.addFragment(RemindersFragment(), "Reminders")

        tvpMainViewPager?.let {
            it.adapter = adapter
        }
    }

    class ViewPageAdapter : FragmentPagerAdapter {
        private var fragmentList1: ArrayList<Fragment> = ArrayList()
        private var fragmentTitleList1: ArrayList<String> = ArrayList()

        constructor(supportFragmentManager: FragmentManager) : super(supportFragmentManager)

        override fun getCount(): Int {
            return fragmentList1.size
        }

        override fun getItem(position: Int): Fragment {
            return fragmentList1.get(position)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return fragmentTitleList1.get(position)
        }

        fun addFragment(fragment: Fragment, title: String){
            fragmentList1.add(fragment)
            fragmentTitleList1.add(title)
        }

    }
}