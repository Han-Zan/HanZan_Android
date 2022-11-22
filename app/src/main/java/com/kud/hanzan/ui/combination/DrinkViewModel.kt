package com.kud.hanzan.ui.combination

import androidx.lifecycle.*
import com.kud.hanzan.HanZanApplication
import com.kud.hanzan.HanZanApplication.Companion.spfManager
import com.kud.hanzan.domain.model.DrinkDetail
import com.kud.hanzan.domain.usecase.drink.GetDrinkUseCase
import com.kud.hanzan.domain.usecase.preferred.DeletePreferredDrinkUseCase
import com.kud.hanzan.domain.usecase.preferred.PostPreferredDrinkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrinkViewModel @Inject constructor(
    private val getDrinkUseCase: GetDrinkUseCase,
    private val postPreferredDrinkUseCase: PostPreferredDrinkUseCase,
    private val deletePreferredDrinkUseCase: DeletePreferredDrinkUseCase,
    private val state: SavedStateHandle
): ViewModel(){
    private val _drinkData = MutableLiveData<DrinkDetail>()
    val drinkData : LiveData<DrinkDetail>
        get() = _drinkData

    private val userIdx = spfManager.getUserIdx()

    init {
        state.get<Long>("drinkIdx")?.let {
            viewModelScope.launch {
                getDrinkUseCase.invoke(it, userIdx)
                    .catch {  }
                    .collectLatest {
                        _drinkData.value = it
                    }
            }
        }
    }

    fun postDrinkLike(drinkIdx: Long){
        viewModelScope.launch {
            postPreferredDrinkUseCase(userIdx, drinkIdx)
                .catch {  }
                .collectLatest{
                    _drinkData.value?.isPrefer = true
                }
        }
    }

    fun deleteDrinkLike(drinkIdx: Long){
        viewModelScope.launch {
            deletePreferredDrinkUseCase(userIdx, drinkIdx)
                .catch {  }
                .collectLatest {
                    _drinkData.value?.isPrefer = false
                }
        }
    }


}