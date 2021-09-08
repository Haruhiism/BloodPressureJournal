package com.pinkmoon.bloodpressurejournal.ui.fragments.new_reading

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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