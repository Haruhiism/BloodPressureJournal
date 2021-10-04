package com.pinkmoon.bloodpressurejournal.db.medication

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class MedicationViewModel(private val repository: MedicationRepository) : ViewModel() {

    val allMedications: LiveData<List<Medication>> = repository.allMedications.asLiveData()

    fun insertMedication(medication: Medication) = viewModelScope.launch {
        repository.insertMedication(medication)
    }

    fun updateMedication(medication: Medication) = viewModelScope.launch {
        repository.updateMedication(medication)
    }

    fun deleteMedication(medication: Medication) = viewModelScope.launch {
        repository.deleteMedication(medication)
    }
}

class MedicationViewModelFactory(private val repository: MedicationRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MedicationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MedicationViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class.")
    }
}