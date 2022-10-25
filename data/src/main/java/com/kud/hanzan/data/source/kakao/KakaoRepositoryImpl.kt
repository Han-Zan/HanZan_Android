package com.kud.hanzan.data.source.kakao

import com.kud.hanzan.domain.model.map.Place
import com.kud.hanzan.domain.model.map.Store
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

    override fun getCategoryPlace(
        longitude: String,
        latitude: String,
        radius: Int,
        page: Int
    ): Flow<List<Store>> = kakaoRemoteDataSource.getCategoryPlace(longitude, latitude, radius, page).map {
        it.documents.filter { s -> s.category_name.contains("술집") || s.category_name.contains("이탈리안") }
            .map { s -> Store(s.id.toLong(), s.place_name, s.category_name, s.distance, " ", s.x, s.y) }
    }
}