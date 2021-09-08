package com.pinkmoon.bloodpressurejournal.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.pinkmoon.bloodpressurejournal.R

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // store the users settings in SharedPreferences
        // getPreferences() retrieves the default SharedPreferences file
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val darkModePref = sharedPref.getBoolean(getString(R.string.saved_dark_mode_key), false)

        val switchDarkMode = view.findViewById<Switch>(R.id.switch_fragment_settings_dark_mode)
        switchDarkMode.isChecked = darkModePref

        switchDarkMode.setOnCheckedChangeListener { _, _ ->
            if (switchDarkMode.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                switchDarkMode.text = resources.getString(R.string.fragment_settings_dark_mode_toggle)
            }

            // store whatever the user selected
            with(sharedPref.edit()) {
                putBoolean(getString(R.string.saved_dark_mode_key), switchDarkMode.isChecked)
                apply()
            }
        }
    }
}