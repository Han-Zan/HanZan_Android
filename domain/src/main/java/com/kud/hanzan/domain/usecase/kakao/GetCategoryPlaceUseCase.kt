package com.kud.hanzan.domain.usecase.kakao

import com.kud.hanzan.domain.repository.KakaoRepository
import javax.inject.Inject

class GetCategoryPlaceUseCase @Inject constructor(
    private val repository: KakaoRepository
) {
    operator fun invoke(longitude: String, latitude: String, page: Int, currentX: Double, currentY: Double) = repository.getCategoryPlace(longitude, latitude, page, currentX, currentY)
}