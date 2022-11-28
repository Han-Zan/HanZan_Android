package com.kud.hanzan.data.source.rating

import com.kud.hanzan.data.entity.rating.RatingDto
import com.kud.hanzan.domain.repository.RatingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RatingRepositoryImpl @Inject constructor(
    private val ratingRemoteDataSource: RatingRemoteDataSource
): RatingRepository {
    override fun postRating(
        combIdx: Long, rating: Float, userIdx: Long
    ): Flow<String> = ratingRemoteDataSource.postRating(RatingDto(combIdx, rating, userIdx))
}