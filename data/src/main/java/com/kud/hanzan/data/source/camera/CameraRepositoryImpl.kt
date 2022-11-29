package com.kud.hanzan.data.source.camera

import com.kud.hanzan.data.entity.camera.CamPostDto
import com.kud.hanzan.data.entity.recommend.RecommandationDto
import com.kud.hanzan.domain.model.Drink
import com.kud.hanzan.domain.model.Food
import com.kud.hanzan.domain.model.RecommendItem
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

    override suspend fun getAllDrinkList(userId: Long)
    : List<Drink> = cameraRemoteDataSource.getAllDrinkList(userId)

    override suspend fun getAllFoodList()
    : List<Food> = cameraRemoteDataSource.getAllFoodList()

    override fun getRecommendations(
        drinkList: List<String>,
        foodList: List<String>,
        userIdx: Long
    ): Flow<List<RecommendItem>> = cameraRemoteDataSource.getRecommend(RecommandationDto(drinkList, foodList, userIdx))
}