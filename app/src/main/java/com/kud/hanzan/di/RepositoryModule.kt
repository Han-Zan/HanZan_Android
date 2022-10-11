package com.kud.hanzan.di

import com.kud.hanzan.data.source.kakao.KakaoRepositoryImpl
import com.kud.hanzan.data.source.kakao.KakaoRemoteDataSource
import com.kud.hanzan.data.source.preferred.PreferredRemoteDataSource
import com.kud.hanzan.data.source.preferred.PreferredRepositoryImpl
import com.kud.hanzan.domain.repository.KakaoRepository
import com.kud.hanzan.domain.repository.PreferredRepository
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

    @Singleton
    @Provides
    fun providesPreferredRepository(preferredRemoteDataSource: PreferredRemoteDataSource) : PreferredRepository{
        return PreferredRepositoryImpl(preferredRemoteDataSource)
    }
}