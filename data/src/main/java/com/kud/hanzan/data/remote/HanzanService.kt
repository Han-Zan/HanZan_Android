package com.kud.hanzan.data.remote

import com.kud.hanzan.data.entity.DrinkInfo
import com.kud.hanzan.data.entity.preferred.CombResult
import com.kud.hanzan.data.entity.preferred.DrinkResult
import com.kud.hanzan.data.entity.preferred.PreferredCombDto
import com.kud.hanzan.data.entity.preferred.PreferredDrinkDto
import com.kud.hanzan.domain.model.HomeData
import com.kud.hanzan.domain.model.UserInfo
import retrofit2.Response
import retrofit2.http.*

interface HanzanService {
    // 좋아요 - 조합
    @GET("/prefcomb")
    suspend fun getPreferredComb(@Query("userId") userId: Long) : CombResult
    @DELETE("/prefcomb")
    suspend fun deletePreferredComb(@Query("combidx") combId: Long, @Query("userid") userId: Long) : String
    @POST("/prefcomb")
    suspend fun postPreferredComb(@Body preferredCombDto: PreferredCombDto) : String

    // 좋아요 - 술
    @GET("/preferred")
    suspend fun getPreferredAlcohol(@Query("userid") userId: Long) : List<DrinkInfo>
    @DELETE("/preferred")
    suspend fun deletePreferredAlcohol(@Query("drinkid") drinkId: Long, @Query("id") userId: Long) : String
    @POST("/preferred")
    suspend fun postPreferredAlcohol(@Body preferredDrinkDto: PreferredDrinkDto) : DrinkResult

    // 술 리스트 화면
    @GET("/product/all")
    suspend fun getDrinkList() : List<DrinkInfo>

    // 홈 화면
    @GET("/api/home/all")
    suspend fun getHomeData(@Query("uid") userId: Long) : HomeData

    // 로그인
    @POST("/userinfo/authrole")
    suspend fun postUserInfo(@Body userInfo: UserInfo) : Response<Long>
}