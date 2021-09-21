package com.pinkmoon.bloodpressurejournal.ui.fragments.statistics

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StatisticsFragmentViewModel : ViewModel() {

    val currentSelectedSpinnerField: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val currentStartDate: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val currentEndDate: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}