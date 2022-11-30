package com.kud.hanzan.data.source.rating

import com.kud.hanzan.data.entity.rating.RatingDto
import kotlinx.coroutines.flow.Flow

interface RatingDataSource {
    fun postRating(ratingDto: RatingDto) : Flow<String>
}