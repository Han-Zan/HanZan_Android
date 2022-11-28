package com.kud.hanzan.domain.usecase.rating

import com.kud.hanzan.domain.repository.RatingRepository
import javax.inject.Inject

class PostRatingUseCase @Inject constructor(
    private val repository: RatingRepository
) {
    operator fun invoke(combIdx: Long, rating: Float, userIdx: Long) = repository.postRating(combIdx, rating, userIdx)
}