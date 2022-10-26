package com.kud.hanzan.data.source.kakao

import com.kud.hanzan.domain.model.map.Place
import com.kud.hanzan.domain.model.map.Store
import com.kud.hanzan.domain.repository.KakaoRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import kotlin.math.*

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
        page: Int,
        currentX: Double,
        currentY: Double
    ): Flow<List<Store>> = kakaoRemoteDataSource.getCategoryPlace(longitude, latitude, page).map {
        it.documents.filter { s -> s.category_name.contains("술집") || s.category_name.contains("이탈리안") }
            .map { s -> Store(s.id.toLong(), s.place_name, s.category_name, getDistance(s.x, s.y, currentX, currentY), " ", s.x, s.y) }
    }

    private fun getDistance(longitude: String, latitude: String, currentX: Double, currentY: Double) : Int{
        val dLat = Math.toRadians(currentY - latitude.toDouble())
        val dLon = Math.toRadians(currentX - longitude.toDouble())
        val a = sin(dLat / 2).pow(2.0) + sin(dLon / 2).pow(2.0) * cos(Math.toRadians(latitude.toDouble())) * cos(Math.toRadians(currentY))
        val c = 2 * asin(sqrt(a))
        return (6372.8 * 1000 * c).toInt()
    }
}