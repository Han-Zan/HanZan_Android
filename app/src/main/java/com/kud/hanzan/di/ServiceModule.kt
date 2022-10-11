package com.kud.hanzan.di

import com.kud.hanzan.data.remote.HanzanService
import com.kud.hanzan.data.remote.KakaoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideKakaoService(@NetworkModule.KakaoRetrofit retrofit: Retrofit) : KakaoService {
        return retrofit.create(KakaoService::class.java)
    }

    @Provides
    @Singleton
    fun provideHanzanService(@NetworkModule.HanZanRetrofit retrofit: Retrofit) : HanzanService{
        return retrofit.create(HanzanService::class.java)
    }
}