package com.kud.hanzan.domain.usecase

import com.kud.hanzan.domain.repository.KakaoRepository
import javax.inject.Inject

class GetRoadAddressUseCase @Inject constructor(
    private val repository: KakaoRepository
){
    operator fun invoke(longitude: String, latitude: String) = repository.getRoadAddress(longitude, latitude)
}