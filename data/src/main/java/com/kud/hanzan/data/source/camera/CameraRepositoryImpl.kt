package com.kud.hanzan.data.source.camera

import com.kud.hanzan.data.entity.camera.CamPostDto
import com.kud.hanzan.domain.repository.CameraRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CameraRepositoryImpl @Inject constructor(
    private val cameraRemoteDataSource: CameraRemoteDataSource
): CameraRepository{
    override fun postCameraDrink(strList: List<String>)
    : Flow<List<String>> = cameraRemoteDataSource.postCameraDrink(CamPostDto(strList, "drink"))

    override fun postCameraFood(strList: List<String>)
    : Flow<List<String>> = cameraRemoteDataSource.postCameraFood(CamPostDto(strList, "food"))
}