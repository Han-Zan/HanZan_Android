package com.kud.hanzan.data.source.camera

import com.kud.hanzan.data.entity.camera.CamPostDto
import kotlinx.coroutines.flow.Flow

interface CameraDataSource {
    fun postCameraDrink(cameraDto: CamPostDto) : Flow<List<String>>
    fun postCameraFood(cameraDto: CamPostDto) : Flow<List<String>>
}