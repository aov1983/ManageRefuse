package com.refund.app.data.local

import androidx.room.*
import com.refund.app.domain.models.PartnerClick

@Dao
interface PartnerClickDao {

    @Query("SELECT * FROM partner_clicks ORDER BY timestamp DESC")
    suspend fun getAllClicks(): List<PartnerClick>

    @Query("SELECT * FROM partner_clicks WHERE partnerId = :partnerId ORDER BY timestamp DESC")
    suspend fun getClicksForPartner(partnerId: String): List<PartnerClick>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(click: PartnerClick): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(clicks: List<PartnerClick>)

    @Delete
    suspend fun delete(click: PartnerClick)
}
