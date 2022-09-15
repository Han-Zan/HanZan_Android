package com.kud.hanzan.data.source

import androidx.lifecycle.Transformations.map
import com.kud.hanzan.data.source.remote.KakaoRemoteDataSource
import com.kud.hanzan.domain.model.Place
import com.kud.hanzan.domain.repository.KakaoRepository
import com.kud.hanzan.domain.utils.error.RemoteErrorEmitter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class KakaoRepositoryImpl @Inject constructor(
    private val kakaoRemoteDataSource: KakaoRemoteDataSource
): KakaoRepository{
    override suspend fun getKeywordPlace(
        emitter: RemoteErrorEmitter,
        keyword: String
    ): Flow<List<Place>> = flow {
        kakaoRemoteDataSource.getKeywordPlace(emitter, keyword)?.map { res ->
            res.documents.map {
                Place(it.place_name, it.category_name, it.x, it.y)
            }
        }
    }
}