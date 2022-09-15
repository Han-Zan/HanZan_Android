package com.kud.hanzan.data.source

import com.kud.hanzan.data.source.remote.KakaoRemoteDataSource
import com.kud.hanzan.domain.model.Place
import com.kud.hanzan.domain.repository.KakaoRepository
import com.kud.hanzan.domain.utils.error.RemoteErrorEmitter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class KakaoRepositoryImpl @Inject constructor(
    private val kakaoRemoteDataSource: KakaoRemoteDataSource
): KakaoRepository{
    override fun getKeywordPlace(
        emitter: RemoteErrorEmitter,
        keyword: String
    ): Flow<Place> = flow {
        kakaoRemoteDataSource.getKeywordPlace(emitter, keyword)?.map { res ->
            res.documents.map {
                emit(Place(it.place_name, it.category_name, it.x, it.y))
            }
        }?.collect()
    }
}