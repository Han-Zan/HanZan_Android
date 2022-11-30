package com.kud.hanzan.domain.usecase.camera

import com.kud.hanzan.domain.repository.CameraRepository
import javax.inject.Inject

class GetRecommendUseCase @Inject constructor(
    private val repository: CameraRepository
) {
    operator fun invoke(drinkList: List<String>, foodList: List<String>, userIdx: Long) = repository.getRecommendations(drinkList, foodList, userIdx)
}