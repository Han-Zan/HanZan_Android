package com.kud.hanzan.data.remote

import com.kud.hanzan.data.entity.preferred.CombResult
import com.kud.hanzan.domain.model.UserInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface HanzanService {
    @GET("/prefcomb")
    suspend fun getPreferredAlcohol(@Query("userId") userId: Long) : CombResult

    @POST("/userinfo/authrole")
    suspend fun postUserInfo(@Body userInfo: UserInfo) : Response<Long>
}