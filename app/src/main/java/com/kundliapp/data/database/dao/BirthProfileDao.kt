package com.kundliapp.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kundliapp.data.database.entities.BirthProfile

@Dao
interface BirthProfileDao {

    @Query("SELECT * FROM birth_profiles ORDER BY createdAt DESC")
    fun getAllProfiles(): LiveData<List<BirthProfile>>

    @Query("SELECT * FROM birth_profiles WHERE id = :id")
    suspend fun getProfileById(id: Long): BirthProfile?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: BirthProfile): Long

    @Update
    suspend fun updateProfile(profile: BirthProfile)

    @Delete
    suspend fun deleteProfile(profile: BirthProfile)

    @Query("DELETE FROM birth_profiles WHERE id = :id")
    suspend fun deleteProfileById(id: Long)

    @Query("SELECT COUNT(*) FROM birth_profiles")
    suspend fun getProfileCount(): Int
}
