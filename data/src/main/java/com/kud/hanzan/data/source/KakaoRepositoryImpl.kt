package com.kud.hanzan.data.source

import android.util.Log
import com.kud.hanzan.data.source.remote.KakaoRemoteDataSource
import com.kud.hanzan.domain.model.Place
import com.kud.hanzan.domain.repository.KakaoRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class KakaoRepositoryImpl @Inject constructor(
    private val kakaoRemoteDataSource: KakaoRemoteDataSource,
): KakaoRepository{
    override fun getKeywordPlace(
        keyword: String
    ): Flow<List<Place>> = flow {
        kakaoRemoteDataSource.getKeywordPlace(keyword).map {
            it.documents.map { p -> Place(p.place_name, p.category_name, p.x, p.y)}.apply { emit(this) }
        }.collect()
//            .flowOn(Dispatchers.IO)
//            .catch { emptyList<Place>() }
//            .collect{
//                it.documents.filter { place -> Place(e) }
//        }
    }
}