package com.kud.hanzan.di

import com.kud.hanzan.data.source.camera.CameraRemoteDataSource
import com.kud.hanzan.data.source.camera.CameraRepositoryImpl
import com.kud.hanzan.data.source.combination.CombinationRemoteDataSource
import com.kud.hanzan.data.source.combination.CombinationRepositoryImpl
import com.kud.hanzan.data.source.drink.DrinkRemoteDataSource
import com.kud.hanzan.data.source.drink.DrinkRepositoryImpl
import com.kud.hanzan.data.source.home.HomeRemoteDataSource
import com.kud.hanzan.data.source.home.HomeRepositoryImpl
import com.kud.hanzan.data.source.kakao.KakaoRepositoryImpl
import com.kud.hanzan.data.source.kakao.KakaoRemoteDataSource
import com.kud.hanzan.data.source.login.LoginDataSource
import com.kud.hanzan.data.source.login.LoginRepositoryImpl
import com.kud.hanzan.data.source.preferred.PreferredRemoteDataSource
import com.kud.hanzan.data.source.preferred.PreferredRepositoryImpl
import com.kud.hanzan.data.source.profile.ProfileDataSource
import com.kud.hanzan.data.source.profile.ProfileRepositoryImpl
import com.kud.hanzan.data.source.store.StoreRemoteDataSource
import com.kud.hanzan.data.source.store.StoreRepositoryImpl
import com.kud.hanzan.domain.repository.*
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

    @Singleton
    @Provides
    fun providesLoginRepository(loginDataSource: LoginDataSource) : LoginRepository{
        return LoginRepositoryImpl(loginDataSource)
    }

    @Singleton
    @Provides
    fun providesCombinationRepository(combinationRemoteDataSource: CombinationRemoteDataSource) : CombinationRepository{
        return CombinationRepositoryImpl(combinationRemoteDataSource)
    }

    @Singleton
    @Provides
    fun providesHomeRepository(homeRemoteDataSource: HomeRemoteDataSource) : HomeRepository{
        return HomeRepositoryImpl(homeRemoteDataSource)
    }

    @Singleton
    @Provides
    fun providesCameraRepository(cameraRemoteDataSource: CameraRemoteDataSource) : CameraRepository {
        return CameraRepositoryImpl(cameraRemoteDataSource)
    }

    @Singleton
    @Provides
    fun providesProfileRepository(ProfileDataSource: ProfileDataSource) : ProfileRepository{
        return ProfileRepositoryImpl(ProfileDataSource)
    }

    @Singleton
    @Provides
    fun providesDrinkRepository(drinkRemoteDataSource: DrinkRemoteDataSource) : DrinkRepository{
        return DrinkRepositoryImpl(drinkRemoteDataSource)
    }

    @Singleton
    @Provides
    fun providesStoreRepository(storeRemoteDataSource: StoreRemoteDataSource) : StoreRepository{
        return StoreRepositoryImpl(storeRemoteDataSource)
    }
}