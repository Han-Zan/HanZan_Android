package com.kud.hanzan.domain.repository

import kotlinx.coroutines.flow.Flow

interface RatingRepository {
    fun postRating(combIdx: Long, rating: Float, userIdx: Long) : Flow<String>
}