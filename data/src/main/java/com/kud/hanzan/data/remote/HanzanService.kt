package com.kud.hanzan.data.remote

import com.kud.hanzan.data.entity.preferred.CombResult
import kotlinx.coroutines.flow.Flow
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Query

interface HanzanService {
    @GET("/prefcomb")
    suspend fun getPreferredComb(@Query("userId") userId: Long) : CombResult
    @DELETE("/prefcomb")
    suspend fun deletePreferredComb(@Query("id") userId: Long, @Query("idx") combId: Long) : String
}