package com.kud.hanzan.data.source.ranking

import android.content.ContentValues
import android.util.Log
import com.kud.hanzan.data.entity.preferred.PreferredCombDto
import com.kud.hanzan.data.remote.HanzanService
import com.kud.hanzan.domain.model.CombinationDto
import com.kud.hanzan.domain.model.CombinationInfo
import com.kud.hanzan.domain.model.Food
import com.kud.hanzan.domain.model.TempPreferedCombDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RankingDataSource @Inject constructor(
    private val hanzanService: HanzanService
){
    suspend fun getCombination(userId: Long, combId: Long) : CombinationInfo {
        var combRes = CombinationInfo(0, 0, "", "", 0, "", "", 0, true, 0, 0F)

        withContext(Dispatchers.IO) {
            runCatching {
                hanzanService.getCombination(userId, combId)
            }.onSuccess {
                combRes = it
            }.onFailure {
                Log.e(ContentValues.TAG, "Getting Combination Information Failure")
                Log.e(ContentValues.TAG, it.toString())
            }
        }
        return combRes
    }
    suspend fun saveCombination(combinationDto: CombinationDto) : String {
        var saveResponse : String = ""
        withContext(Dispatchers.IO) {
            runCatching {
                hanzanService.saveCombination(combinationDto)
            }.onSuccess {
                saveResponse = "Success"
                Log.e(ContentValues.TAG, "Combination Modify Success")
            }.onFailure {
                saveResponse = "Failure"
                Log.e(ContentValues.TAG, "Combination Modify Failure")
            }
        }
        return saveResponse
    }
    suspend fun listAll(userId: Long) : List<CombinationInfo> {
        var combinations: List<CombinationInfo> = emptyList()
        withContext(Dispatchers.IO) {
            runCatching {
                hanzanService.listAll(userId)
            }.onSuccess {
                combinations = it
            }.onFailure {

            }
        }
        return combinations
    }

    suspend fun deletePreferredComb(combId: Long, userId: Long) : String {
        var res = ""
        withContext(Dispatchers.IO) {
            runCatching {
                hanzanService.deletePreferredComb(combId, userId)
            }.onSuccess {
                Log.e(ContentValues.TAG, it)
                res = "Success"
            }.onFailure {
                Log.e(ContentValues.TAG, it.toString())
                res = "Failure"
            }
        }
        return res
    }

    suspend fun postPreferredComb(preferredCombDto: TempPreferedCombDto) : String {
        var res = ""
        withContext(Dispatchers.IO) {
            runCatching {
                hanzanService.postPreferredComb(PreferredCombDto(preferredCombDto.combid, preferredCombDto.uid))
            }.onSuccess {
                Log.e(ContentValues.TAG, it)
                res = "Success"
            }.onFailure {
                Log.e(ContentValues.TAG, it.toString())
                res = "Failure"
            }
        }
        return res
    }
}