package com.kud.hanzan.di

import android.content.Context
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3Client
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AwsModule {
    @Provides
    @Singleton
    fun provideTransferUtility(s3Client: AmazonS3Client, @ApplicationContext context: Context) : TransferUtility{
        return TransferUtility.builder()
            .s3Client(s3Client)
            .context(context)
            .build()
    }

    @Provides
    @Singleton
    fun provideS3Client(awsCredentials: BasicAWSCredentials) : AmazonS3Client{
        return AmazonS3Client(awsCredentials, Region.getRegion(Regions.AP_NORTHEAST_2))
    }

    @Provides
    @Singleton
    fun provideAwsCredential() : BasicAWSCredentials = BasicAWSCredentials("AKIAV3NWTKW2VVMCLGH7", "sZDF8T3jYiknljrqx2uil+78Dmu2gKsiWE12wKfD")
}