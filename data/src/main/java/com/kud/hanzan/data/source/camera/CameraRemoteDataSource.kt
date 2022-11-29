package com.kud.hanzan.data.source.camera

import com.kud.hanzan.data.entity.camera.CamPostDto
import com.kud.hanzan.data.entity.recommend.RecommandationDto
import com.kud.hanzan.data.remote.HanzanService
import com.kud.hanzan.domain.model.Drink
import com.kud.hanzan.domain.model.Food
import com.kud.hanzan.domain.model.RecommendItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
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

    override fun getRecommend(recommandationDto: RecommandationDto)
    : Flow<List<RecommendItem>> = flow {
        emit(hanzanService.getRecommendations(recommandationDto).sortedByDescending { recommendItem -> recommendItem.combScore }
            .take(20))
    }.flowOn(Dispatchers.IO)

    override suspend fun getAllDrinkList(userId: Long) : List<Drink> {
        var drinkList: List<Drink> = emptyList()
        withContext(Dispatchers.IO) {
            runCatching {
                hanzanService.getDrinkList(userId)
            }.onSuccess {
                drinkList = it
            }.onFailure {

            }
        }
        return drinkList
    }
    override suspend fun getAllFoodList() : List<Food> {
        var foodList: List<Food> = emptyList()
        withContext(Dispatchers.IO) {
            runCatching {
                hanzanService.getAllFood()
            }.onSuccess {
                foodList = it
            }.onFailure {

            }
        }
        return foodList
    }
}
