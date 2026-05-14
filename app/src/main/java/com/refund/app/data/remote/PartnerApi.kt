package com.refund.app.data.remote

import com.refund.app.domain.models.Partner
import retrofit2.http.GET

/**
 * API интерфейс для получения списка партнёров
 */
interface PartnerApi {
    
    @GET("partners")
    suspend fun getPartners(): List<PartnerDto>
}

/**
 * DTO для получения данных от сервера
 */
data class PartnerDto(
    val id: String,
    val name: String,
    val urlTemplate: String,
    val matchServiceName: String,
    val iconUrl: String?
)
