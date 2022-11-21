package com.kud.hanzan.data.remote

import com.kud.hanzan.data.entity.DrinkInfo
import com.kud.hanzan.data.entity.camera.CamPostDto
import com.kud.hanzan.data.entity.preferred.CombResult
import com.kud.hanzan.data.entity.preferred.DrinkResult
import com.kud.hanzan.data.entity.preferred.PreferredCombDto
import com.kud.hanzan.data.entity.preferred.PreferredDrinkDto
import com.kud.hanzan.data.entity.store.StoreKakaoData
import com.kud.hanzan.domain.model.Drink
import com.kud.hanzan.domain.model.HomeData
import com.kud.hanzan.domain.model.*
import com.kud.hanzan.domain.model.map.StoreCombData
import com.kud.hanzan.domain.repository.CombinationRepository
import retrofit2.Response
import retrofit2.http.*

interface HanzanService {
    // 프로필
    @GET("/userinfo")
    suspend fun getUser(@Query("Id") userId: Long) : Response<User>
    @PUT("/userinfo/nickname")
    suspend fun changeUserNickName(@Query("userIdx") userId: Long, @Query("userName") userName: String) : String
    @PUT("/userinfo/profile")
    suspend fun changeUserProfile(@Query("userIdx") userId: Long, @Query("userImg") userImg: String) : String
    @DELETE("/userinfo")
    suspend fun deleteUser(@Query("userId") userId: Long) : String

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

    // 조합 둘러보기
    @GET("/recommands")
    suspend fun recommandation(@Query("drinkName") drinkName: String, @Query("foodName") foodName: String, @Query("userId") userId: Long) : Comb

    // 술 리스트 화면
    @GET("/product/all")
    suspend fun getDrinkList(@Query("userId") userId: Long) : List<Drink>

    // 술 상세 화면
    @GET("/product")
    suspend fun getDrinkDetail(@Query("drinkIdx") drinkIdx: Long, @Query("userIdx") userIdx : Long) : DrinkDetail

    // 안주 리스트 화면
    @GET("/food/all")
    suspend fun getAllFood() : List<Food>

    // 홈 화면
    @GET("/api/home/all")
    suspend fun getHomeData(@Query("uid") userId: Long) : HomeData

    // 카메라 화면
    @POST("/cam")
    suspend fun postCameraList(@Body camPostDto: CamPostDto) : List<String>

    // 가게 화면
    @POST("/stores")
    suspend fun postStore(@Body store: StoreKakaoData) : Long
    @GET("/stores")
    suspend fun getStore(@Query("kakaoId") storeId: String) : StoreCombData

}