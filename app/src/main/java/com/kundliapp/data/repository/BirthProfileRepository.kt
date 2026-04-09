package com.kundliapp.data.repository

import androidx.lifecycle.LiveData
import com.kundliapp.data.database.dao.BirthProfileDao
import com.kundliapp.data.database.entities.BirthProfile

class BirthProfileRepository(private val dao: BirthProfileDao) {

    val allProfiles: LiveData<List<BirthProfile>> = dao.getAllProfiles()

    suspend fun getProfileById(id: Long): BirthProfile? = dao.getProfileById(id)

    suspend fun insertProfile(profile: BirthProfile): Long = dao.insertProfile(profile)

    suspend fun updateProfile(profile: BirthProfile) = dao.updateProfile(profile)

    suspend fun deleteProfile(profile: BirthProfile) = dao.deleteProfile(profile)

    suspend fun deleteProfileById(id: Long) = dao.deleteProfileById(id)

    suspend fun getProfileCount(): Int = dao.getProfileCount()
}
