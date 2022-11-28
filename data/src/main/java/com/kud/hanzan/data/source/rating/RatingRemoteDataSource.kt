package com.kud.hanzan.data.source.rating

import com.kud.hanzan.data.entity.rating.RatingDto
import com.kud.hanzan.data.remote.HanzanService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RatingRemoteDataSource @Inject constructor(
    private val hanzanService: HanzanService
) : RatingDataSource{
    override fun postRating(
        ratingDto: RatingDto
    ): Flow<String> = flow {
        emit(hanzanService.postRating(ratingDto))
    }.flowOn(Dispatchers.IO)
}