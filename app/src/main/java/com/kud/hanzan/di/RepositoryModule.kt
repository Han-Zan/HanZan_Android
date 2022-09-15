package com.kud.hanzan.di

import com.kud.hanzan.data.source.KakaoRepositoryImpl
import com.kud.hanzan.data.source.remote.KakaoRemoteDataSource
import com.kud.hanzan.domain.repository.KakaoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun providesKakaoRepository(kakaoRemoteDataSource: KakaoRemoteDataSource) :KakaoRepository{
        return KakaoRepositoryImpl(kakaoRemoteDataSource)
    }
}