package com.kud.hanzan.data.source

import android.util.Log
import com.kud.hanzan.data.source.remote.KakaoRemoteDataSource
import com.kud.hanzan.domain.model.Place
import com.kud.hanzan.domain.repository.KakaoRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class KakaoRepositoryImpl @Inject constructor(
    private val kakaoRemoteDataSource: KakaoRemoteDataSource,
): KakaoRepository {
    override fun getKeywordPlace(
        keyword: String
    ): Flow<List<Place>> = kakaoRemoteDataSource.getKeywordPlace(keyword).map {
        it.documents.map { p -> Place(p.place_name, p.category_name, p.x, p.y) }
    }

    override fun getRoadAddress(
        longitude: String, latitude: String
    ): Flow<String> = kakaoRemoteDataSource.getRoadAddress(longitude, latitude).map { res ->
        res.documents[0].address?.let { r -> "${r.region_2depth_name} ${r.region_3depth_name}"}!!
    }
}