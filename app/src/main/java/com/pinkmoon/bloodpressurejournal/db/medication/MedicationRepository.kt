package com.pinkmoon.bloodpressurejournal.db.medication

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class MedicationRepository(private val medicationDao: MedicationDao) {

    val allMedications: Flow<List<Medication>> = medicationDao.getAllMedications()

    @WorkerThread
    suspend fun insertMedication(medication: Medication){
        medicationDao.insertMedication(medication)
    }

    @WorkerThread
    suspend fun updateMedication(medication: Medication){
        medicationDao.updateMedication(medication)
    }

    @WorkerThread
    suspend fun deleteMedication(medication: Medication){
        medicationDao.deleteMedication(medication)
    }
}