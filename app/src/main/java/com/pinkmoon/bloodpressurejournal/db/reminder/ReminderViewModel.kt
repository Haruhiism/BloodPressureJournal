package com.pinkmoon.bloodpressurejournal.db.reminder

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class ReminderViewModel(private val reminderRepository: ReminderRepository) : ViewModel() {

    val allReminders: LiveData<List<Reminder>> = reminderRepository.allReminders.asLiveData()

    fun insertReminder(reminder: Reminder) = viewModelScope.launch {
        reminderRepository.insertReminder(reminder)
    }

    fun updateReminder(reminder: Reminder) = viewModelScope.launch {
        reminderRepository.updateReminder(reminder)
    }

    fun deleteReminder(reminder: Reminder) = viewModelScope.launch {
        reminderRepository.deleteReminder(reminder)
    }
}

class ReminderViewModelFactory(private val reminderRepository: ReminderRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ReminderViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return ReminderViewModel(reminderRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class.")
    }

}