package com.kud.hanzan.domain.usecase.camera

import com.kud.hanzan.domain.repository.CameraRepository
import javax.inject.Inject

class PostCameraFoodUseCase @Inject constructor(
    private val repository: CameraRepository
) {
    operator fun invoke(strList: List<String>) = repository.postCameraFood(strList)
}