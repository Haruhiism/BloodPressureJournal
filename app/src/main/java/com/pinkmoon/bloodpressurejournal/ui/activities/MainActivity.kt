package com.pinkmoon.bloodpressurejournal.ui.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pinkmoon.bloodpressurejournal.BloodPressureJournalApplication
import com.pinkmoon.bloodpressurejournal.NavGraphDirections
import com.pinkmoon.bloodpressurejournal.R
import com.pinkmoon.bloodpressurejournal.db.bp_reading.BPReading
import com.pinkmoon.bloodpressurejournal.db.bp_reading.BPReadingListAdapter
import com.pinkmoon.bloodpressurejournal.db.bp_reading.BPReadingViewModel
import com.pinkmoon.bloodpressurejournal.db.bp_reading.BPReadingViewModelFactory
import com.pinkmoon.bloodpressurejournal.ui.fragments.dialogs.SelectNumOfReadingsDialog

class MainActivity : AppCompatActivity(), SelectNumOfReadingsDialog.SelectNumOfReadingsDialogListener {

    private val newBPReadingActivityRequestCode = 1

    private val bpReadingViewModel: BPReadingViewModel by viewModels {
        BPReadingViewModelFactory((application as BloodPressureJournalApplication).bpReadingRepository)
    }

    private lateinit var navController: NavController

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up the toolbar
        var toolbar = findViewById<Toolbar>(R.id.tb_activity_main_toolbar)
        var bottomNav = findViewById<BottomNavigationView>(R.id.activity_main_bottom_nav)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.frag_cont_nav_host_fragment) as NavHostFragment

        navController = navHostFragment.findNavController()
        setSupportActionBar(toolbar)

        // Set up bottom navigation bar
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.statisticsFragment) // set of top level destinations
        )

        // connects the action bar (toolbar) & bottom nav bar to the nav controller
        setupActionBarWithNavController(navController, appBarConfiguration)

        bottomNav.setupWithNavController(navController)

//        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
//        val adapter = BPReadingListAdapter()
//        recyclerView.adapter = adapter
//        recyclerView.layoutManager = LinearLayoutManager(this)
//
//        bpReadingViewModel.allBpReadings.observe(this, { bpReading ->
//            bpReading.let { adapter.submitList(it) }
//        })

//        val fab = findViewById<FloatingActionButton>(R.id.fab)
//        fab.setOnClickListener {
//            var dialogSelectNumOfReadingsDialog = SelectNumOfReadingsDialog()
//            dialogSelectNumOfReadingsDialog.show(supportFragmentManager, "dialogSelectNumOfReadingsDialog")
////            val intent = Intent(this@MainActivity, NewBPReadingActivity::class.java)
////            startActivityForResult(intent, newBPReadingActivityRequestCode)
//        }
    }

    // Options menu
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options, menu) // this is how we activate the menu (or link it up rather)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.termsAndConditions) {
            val action = NavGraphDirections.actionGlobalTermsFragment()
            navController.navigate(action)
            return true
        } else {
            // this navigates to the destination we clicked if the ids match
            item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == newBPReadingActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(NewBPReadingActivity.EXTRA_REPLY)?.let {
                val reading = BPReading(it.toInt(), 74, 69)
                bpReadingViewModel.insert(reading)
            }
        } else {
            Toast.makeText(
                applicationContext,
                "Failed to insert.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun passUserReadingSelection(numOfReadings: Int) {

//        Toast.makeText(
//            applicationContext,
//            "User selected $numOfReadings reading(s).",
//            Toast.LENGTH_SHORT
//        ).show()
    }
}