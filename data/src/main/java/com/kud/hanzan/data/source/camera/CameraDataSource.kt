package com.kud.hanzan.data.source.camera

import com.kud.hanzan.data.entity.camera.CamPostDto
import com.kud.hanzan.data.entity.recommend.RecommandationDto
import com.kud.hanzan.domain.model.Drink
import com.kud.hanzan.domain.model.Food
import com.kud.hanzan.domain.model.RecommendItem
import kotlinx.coroutines.flow.Flow

interface CameraDataSource {
    fun postCameraDrink(cameraDto: CamPostDto) : Flow<List<String>>
    fun postCameraFood(cameraDto: CamPostDto) : Flow<List<String>>
    suspend fun getAllDrinkList(userId: Long) : List<Drink>
    suspend fun getAllFoodList() : List<Food>
    fun getRecommend(recommandationDto: RecommandationDto) : Flow<List<RecommendItem>>
}