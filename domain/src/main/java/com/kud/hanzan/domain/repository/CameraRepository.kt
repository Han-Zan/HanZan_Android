package com.kud.hanzan.domain.repository

import com.kud.hanzan.domain.model.Drink
import com.kud.hanzan.domain.model.Food
import com.kud.hanzan.domain.model.RecommendItem
import kotlinx.coroutines.flow.Flow

interface CameraRepository {
    fun postCameraDrink(strList: List<String>) : Flow<List<String>>
    fun postCameraFood(strList: List<String>) : Flow<List<String>>
    suspend fun getAllDrinkList(userId: Long) : List<Drink>
    suspend fun getAllFoodList() : List<Food>
    fun getRecommendations(drinkList: List<String>, foodList: List<String>, userIdx: Long) : Flow<List<RecommendItem>>
}