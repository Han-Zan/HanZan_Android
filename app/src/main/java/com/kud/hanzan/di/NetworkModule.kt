package com.kud.hanzan.di

import com.kakao.sdk.network.ApiFactory.loggingInterceptor
import com.kud.hanzan.utils.Utils.BASE_URL
import com.kud.hanzan.utils.Utils.KAKAO_BASE_URL
import com.kud.hanzan.utils.Utils.KAKAO_REST_API_KEY
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class KakaoRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class HanZanRetrofit

    @Provides
    @Singleton
    @KakaoRetrofit
    fun provideKakaoRetrofit(gsonConverterFactory: GsonConverterFactory, client: OkHttpClient) : Retrofit{
        return Retrofit.Builder()
            .baseUrl(KAKAO_BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    @HanZanRetrofit
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory, client: OkHttpClient) : Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory() : GsonConverterFactory{
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    @KakaoRetrofit
    fun provideKakaoHttpClient(loggingInterceptor: HttpLoggingInterceptor, headerInterceptor: Interceptor) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(headerInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @HanZanRetrofit
    fun provideHttpClient(loggingInterceptor: HttpLoggingInterceptor) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Singleton
    fun provideHeaderInterceptor() : Interceptor = Interceptor {
            chain -> chain.run{
                proceed(
                    request()
                        .newBuilder()
                        .addHeader("Authorization", KAKAO_REST_API_KEY)
                        .build()
                )
        }
    }
}