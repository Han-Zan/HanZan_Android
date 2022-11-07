package com.kud.hanzan.data.remote

import com.kud.hanzan.domain.model.UserInfo
import com.kud.hanzan.domain.model.UserResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface HanzanLoginService {
    // 로그인 & 회원가입
    @POST("/auth/login")
    suspend fun postUserInfo(@Body userInfo: UserInfo) : Response<UserResponseDto?>
    @POST("/auth/signCheck")
    suspend fun checkUserAccount(@Query("kakao_id") userId: Long) : Response<UserResponseDto?>
}