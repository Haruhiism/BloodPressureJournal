package com.pinkmoon.bloodpressurejournal.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.pinkmoon.bloodpressurejournal.R

class BPReadingHolderFragment : Fragment(R.layout.fragment_bp_reading_holder) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Create the object of Toolbar, ViewPager and
        // TabLayout and use “findViewById()” method*/
        var tab_toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        var tab_viewpager = view.findViewById<ViewPager>(R.id.tab_viewpager)
        var tab_tablayout = view.findViewById<TabLayout>(R.id.tab_tablayout)

        setupViewPager(tab_viewpager)
        tab_tablayout.setupWithViewPager(tab_viewpager)
    }

    private fun setupViewPager(viewpager: ViewPager) {
        var adapter: ViewPageAdapter = ViewPageAdapter(parentFragmentManager) // maybe child instead?

        adapter.addFragment(NewReadingFragment(), "Reading 1")
        adapter.addFragment(NewReadingFragment(), "Reading 2")
        adapter.addFragment(NewReadingFragment(), "Reading 3")

        viewpager.adapter = adapter
    }

    class ViewPageAdapter : FragmentPagerAdapter {

        private var fragmentList1: ArrayList<Fragment> = ArrayList()
        private var fragmentTitleList1: ArrayList<String> = ArrayList()

        public constructor(supportFragmentManager: FragmentManager)
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
            fragmentList1.add(fragment)
            fragmentTitleList1.add(title)
        }
    }
}