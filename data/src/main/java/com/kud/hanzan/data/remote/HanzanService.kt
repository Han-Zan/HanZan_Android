package com.kud.hanzan.data.remote

import com.kud.hanzan.data.entity.Drink
import com.kud.hanzan.data.entity.preferred.CombResult
import com.kud.hanzan.data.entity.preferred.DrinkResult
import com.kud.hanzan.data.entity.preferred.PreferredCombDto
import com.kud.hanzan.data.entity.preferred.PreferredDrinkDto
import com.kud.hanzan.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.*

interface HanzanService {
    @GET("/prefcomb")
    suspend fun getPreferredComb(@Query("userId") userId: Long) : CombResult
    @DELETE("/prefcomb")
    suspend fun deletePreferredComb(@Query("id") userId: Long, @Query("idx") combId: Long) : String
    @POST("/prefcomb")
    suspend fun postPreferredComb(@Body preferredCombDto: PreferredCombDto) : String

    @GET("/preferred")
    suspend fun getPreferredAlcohol(@Query("userid") userId: Long) : List<Drink>
    @DELETE("/preferred")
    suspend fun deletePreferredAlcohol(@Query("drinkid") drinkId: Long, @Query("id") userId: Long) : String
    @POST("/preferred")
    suspend fun postPreferredAlcohol(@Body preferredDrinkDto: PreferredDrinkDto) : DrinkResult

    @POST("/userinfo/authrole")
    suspend fun postUserInfo(@Body userInfo: UserInfo) : Response<Long>
}