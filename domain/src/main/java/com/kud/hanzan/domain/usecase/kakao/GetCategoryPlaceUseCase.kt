package com.kud.hanzan.domain.usecase.kakao

import com.kud.hanzan.domain.repository.KakaoRepository
import javax.inject.Inject

class GetCategoryPlaceUseCase @Inject constructor(
    private val repository: KakaoRepository
) {
    operator fun invoke(longitude: String, latitude: String, radius: Int, page: Int) = repository.getCategoryPlace(longitude, latitude, radius, page)
}