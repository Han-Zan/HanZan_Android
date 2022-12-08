package com.kud.hanzan.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kud.hanzan.domain.model.HomeComb
import com.kud.hanzan.domain.model.HomeCombination
import com.kud.hanzan.domain.model.HomeData
import com.kud.hanzan.domain.usecase.home.GetHomeDataUseCase
import com.kud.hanzan.domain.usecase.preferred.DeletePreferredCombUseCase
import com.kud.hanzan.domain.usecase.preferred.PostPreferredCombUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Thread.State
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeDataUseCase: GetHomeDataUseCase,
    private val deletePreferredCombUseCase: DeletePreferredCombUseCase,
    private val postPreferredCombUseCase: PostPreferredCombUseCase
) : ViewModel() {
    private var _rankCombData = MutableStateFlow<List<HomeComb>>(emptyList())
    val rankCombData : StateFlow<List<HomeComb>>
        get() = _rankCombData

    private var _userName = MutableStateFlow<String>(" ")
    val userName : StateFlow<String>
        get() = _userName

    private var _userSbti = MutableStateFlow<String>("")
    val userSbti : StateFlow<String>
        get() = _userSbti

    private var _errorMsg = MutableStateFlow<String>("")
    val errorMsg : StateFlow<String>
        get() = _errorMsg

    fun getData(userIdx: Long){
        viewModelScope.launch {
            homeDataUseCase(userIdx)
                .catch {
                    _userName.value = "재윤아 서버켜줘"
                    _userSbti.value = "고독한 미식가"
                    _rankCombData.value = listOf(
                        HomeComb(null, "일반 소주", null,"닭발",  1, true, 4),
                        HomeComb(null, "카스", null, "치킨", 2, false, 3),
                        HomeComb(null,"국순당막걸리",  null,"파전", 3, true, 2),
                    )
                }
                .collectLatest{
                    _rankCombData.value = it.lists
                    _userName.value = it.name
                    _userSbti.value = it.sbtiString
                }
        }
    }

    fun deleteCombLike(userIdx: Long, combIdx: Long){
        viewModelScope.launch {
            deletePreferredCombUseCase(userIdx, combIdx)
                .catch {
                    it.cause?.let{ _errorMsg.value = "삭제 요청 중 오류가 발생하였습니다."} }
                .collect()
        }
    }

    fun postCombLike(userIdx: Long, combIdx: Long){
        viewModelScope.launch {
            postPreferredCombUseCase(userIdx, combIdx)
                .catch {
                    it.cause?.let { _errorMsg.value = "좋아요 요청 중 오류가 발생하였습니다." } }
                .collect()
        }
    }
}