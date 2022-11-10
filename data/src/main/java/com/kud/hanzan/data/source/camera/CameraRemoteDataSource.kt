package com.kud.hanzan.data.source.camera

import com.kud.hanzan.data.entity.camera.CamPostDto
import com.kud.hanzan.data.remote.HanzanService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CameraRemoteDataSource @Inject constructor(
    private val hanzanService: HanzanService
) : CameraDataSource {
    override fun postCameraDrink(cameraDto: CamPostDto)
    : Flow<List<String>> = flow {
        emit(hanzanService.postCameraList(cameraDto))
    }.flowOn(Dispatchers.IO)

    override fun postCameraFood(cameraDto: CamPostDto)
    : Flow<List<String>> = flow {
        emit(hanzanService.postCameraList(cameraDto))
    }.flowOn(Dispatchers.IO)

}
