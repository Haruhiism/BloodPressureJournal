package com.pinkmoon.bloodpressurejournal.db.join_redication_reminder

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import java.lang.IllegalArgumentException

class JoinMedicationReminderViewModel(private val joinMedicationReminderRepository: JoinMedicationReminderRepository) : ViewModel() {
    val allJoinMedicationReminders: LiveData<List<JoinMedicationReminder>> =
        joinMedicationReminderRepository.allMedicationReminders.asLiveData()

    fun getMedicationRemindersByName(mName: String) : LiveData<List<JoinMedicationReminder>> =
        joinMedicationReminderRepository.getMedicationRemindersByName(mName).asLiveData()
}

class JoinMedicationReminderViewModelFactory(
    private val joinMedicationReminderRepository: JoinMedicationReminderRepository) :
        ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(JoinMedicationReminderViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return JoinMedicationReminderViewModel(joinMedicationReminderRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class.")
    }

}