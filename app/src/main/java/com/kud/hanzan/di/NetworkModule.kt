package com.kud.hanzan.di

import com.kakao.sdk.network.ApiFactory.loggingInterceptor
import com.kud.hanzan.HanZanApplication.Companion.spfManager
import com.kud.hanzan.utils.AuthInterceptor
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
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    // 카카오 API 담당 레트로핏
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class KakaoRetrofit

    // 한잔 서비스 API 담당 레트로핏
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class HanZanRetrofit

    // 한잔 로그인 전용 API 레트로핏
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class HanZanLoginRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class KakaoInterceptor

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class HanZanInterceptor

    @Provides
    @Singleton
    fun provideAuthInterceptor(authInterceptor: AuthInterceptor) : Interceptor = authInterceptor

    @Provides
    @Singleton
    @KakaoRetrofit
    fun provideKakaoRetrofit(gsonConverterFactory: GsonConverterFactory, @KakaoRetrofit client: OkHttpClient) : Retrofit{
        return Retrofit.Builder()
            .baseUrl(KAKAO_BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    @HanZanRetrofit
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory, @HanZanRetrofit client: OkHttpClient) : Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    @HanZanLoginRetrofit
    fun provideLoginRetrofit(gsonConverterFactory: GsonConverterFactory, @HanZanLoginRetrofit client: OkHttpClient) : Retrofit{
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
    fun provideKakaoHttpClient(loggingInterceptor: HttpLoggingInterceptor, @KakaoInterceptor headerInterceptor: Interceptor) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(headerInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @HanZanLoginRetrofit
    fun provideLoginHttpClient(loggingInterceptor: HttpLoggingInterceptor) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @HanZanRetrofit
    fun provideHttpClient(loggingInterceptor: HttpLoggingInterceptor, authInterceptor: AuthInterceptor) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
//            .apply {
//                if(spfManager.checkUserToken()) addInterceptor(headerInterceptor.get())
//            }
            .build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Singleton
    @KakaoInterceptor
    fun provideKakaoHeaderInterceptor() : Interceptor = Interceptor {
            chain -> chain.run{
                proceed(
                    request()
                        .newBuilder()
                        .addHeader("Authorization", KAKAO_REST_API_KEY)
                        .build()
                )
        }
    }

//    @Provides
//    @Singleton
//    @HanZanInterceptor
//    fun provideHeaderInterceptor(): Interceptor = Interceptor { chain ->
//        chain.run {
//            proceed(
//                request()
//                    .newBuilder()
//                    .addHeader("Authorization", spfManager.getUserToken())
//                    .build()
//            )
//        }
//    }
}