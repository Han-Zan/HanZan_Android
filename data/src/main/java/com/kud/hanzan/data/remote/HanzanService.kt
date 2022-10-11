package com.kud.hanzan.data.remote

import com.kud.hanzan.data.entity.preferred.CombResult
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface HanzanService {
    @GET("/prefcomb")
    suspend fun getPreferredAlcohol(@Query("userId") userId: Long) : CombResult
}