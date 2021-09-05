package com.pinkmoon.bloodpressurejournal.db.bp_reading

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class BPReadingViewModel(private val repository: BPReadingRepository) : ViewModel() {

    val allBpReadings: LiveData<List<BPReading>> = repository.allReadings.asLiveData()

    fun insert(bpReading: BPReading) = viewModelScope.launch {
        repository.insert(bpReading)
    }
}

class BPReadingViewModelFactory(private val repository: BPReadingRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(BPReadingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BPReadingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class.")
    }
}