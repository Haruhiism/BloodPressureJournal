package com.pinkmoon.bloodpressurejournal.ui.fragments.new_reading

import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pinkmoon.bloodpressurejournal.BloodPressureJournalApplication
import com.pinkmoon.bloodpressurejournal.db.bp_reading.BPReadingViewModel
import com.pinkmoon.bloodpressurejournal.db.bp_reading.BPReadingViewModelFactory

class NewReadingFragmentViewModel : ViewModel() {

    var systolicValue = 120
    var diastolicValue = 80
    var pulseValue = 72

    val currentSystolicValue: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val currentDiastolicValue: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val currentPulseValue: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

}