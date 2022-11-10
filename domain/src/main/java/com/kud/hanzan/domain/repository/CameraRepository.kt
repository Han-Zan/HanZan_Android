package com.kud.hanzan.domain.repository

import kotlinx.coroutines.flow.Flow

interface CameraRepository {
    fun postCameraDrink(strList: List<String>) : Flow<List<String>>
    fun postCameraFood(strList: List<String>) : Flow<List<String>>
}