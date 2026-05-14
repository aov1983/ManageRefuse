package com.refund.app.data.local

import androidx.room.*
import com.refund.app.domain.models.Partner
import kotlinx.coroutines.flow.Flow

@Dao
interface PartnerDao {

    @Query("SELECT * FROM partners WHERE isActive = 1 ORDER BY name")
    fun getAllActivePartners(): Flow<List<Partner>>

    @Query("SELECT * FROM partners WHERE id = :id")
    suspend fun getPartnerById(id: String): Partner?

    @Query("SELECT * FROM partners WHERE LOWER(matchServiceName) LIKE '%' || LOWER(:serviceName) || '%' AND isActive = 1")
    suspend fun findPartnerForService(serviceName: String): Partner?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(partner: Partner)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(partners: List<Partner>)

    @Delete
    suspend fun delete(partner: Partner)

    @Query("DELETE FROM partners")
    suspend fun deleteAll()

    @Query("SELECT MAX(lastUpdated) FROM partners")
    suspend fun getLastUpdateTime(): Long?
}
