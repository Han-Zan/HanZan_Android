package com.kud.hanzan.ui.like

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kud.hanzan.R
import com.kud.hanzan.domain.model.Alcohol
import com.kud.hanzan.domain.model.Combination
import com.kud.hanzan.domain.usecase.preferred.DeletePreferredCombUseCase
import com.kud.hanzan.domain.usecase.preferred.GetPreferredCombUseCase
import com.kud.hanzan.domain.usecase.preferred.PostPreferredCombUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.jetbrains.annotations.TestOnly
import javax.inject.Inject

@HiltViewModel
class LikeViewModel @Inject constructor(
    private val getCombUseCase : GetPreferredCombUseCase,
    private val deleteCombUseCase: DeletePreferredCombUseCase,
    private val postCombUseCase: PostPreferredCombUseCase
) : ViewModel() {
    private var _alcoholData = MutableStateFlow<List<Alcohol>>(emptyList())
    val alcoholData : StateFlow<List<Alcohol>>
        get() = _alcoholData
    private var totalAlcoholData = listOf<Alcohol>()

    private var _combData = MutableStateFlow<List<Combination>>(emptyList())
    val combData : StateFlow<List<Combination>>
        get() = _combData
    private var totalCombData = listOf<Combination>()

    private var searchKeyword: String? = null

    init {
        getComb(1)
        getAlcohol(1)
    }

    fun getComb(userId: Long){
        viewModelScope.launch {
            getCombUseCase(userId)
                .catch { _combData.value = listOf(
                    Combination("일반소주", null, "닭발", null,   0, 0f, "누구도 부정할 수 없는\n궁극의 그 조합!"),
                    Combination("카스", null, "치킨", null, 0, 0f, "치맥 안 먹어본 사람 있어?")
                ).also {
                    totalCombData = it
                } }
                .collect{
                    _combData.value = it
                    totalCombData = it
                }
        }
    }

    fun getAlcohol(userId: Long){
        _alcoholData.value = listOf(Alcohol("고든", "양주", 3, 4.9,  R.drawable.src_godons, "태그"),
            Alcohol("참이슬", "소주", 1, 4.5, R.drawable.src_soju, "깔끔"),
            Alcohol("모스카토 다스티", "와인", 4, 4.2,  R.drawable.src_wine, "산미"),
            Alcohol("참이슬", "소주", 1, 4.5, R.drawable.src_soju, "깔끔"),
            Alcohol("고든", "양주", 3, 4.9,  R.drawable.src_godons, "태그"),
            Alcohol("모스카토 다스티", "와인", 4, 4.2,  R.drawable.src_wine, "산미"),
            Alcohol("참이슬", "소주", 1, 4.5, R.drawable.src_soju, "깔끔"),
            Alcohol("고든", "양주", 3, 4.9,  R.drawable.src_godons, "태그"),
            Alcohol("모스카토 다스티", "와인", 4, 4.2,  R.drawable.src_wine, "산미"),
            Alcohol("고든", "양주", 3, 4.9,  R.drawable.src_godons, "태그"),
            Alcohol("참이슬", "소주", 1, 4.5, R.drawable.src_soju, "깔끔"),
            Alcohol("모스카토 다스티", "와인", 4, 4.2,  R.drawable.src_wine, "산미"),
            Alcohol("참이슬", "소주", 1, 4.5, R.drawable.src_soju, "깔끔"),
            Alcohol("고든", "양주", 3, 4.9,  R.drawable.src_godons, "태그"),
            Alcohol("모스카토 다스티", "와인", 4, 4.2,  R.drawable.src_wine, "산미"),
            Alcohol("참이슬", "소주", 1, 4.5, R.drawable.src_soju, "깔끔"),
            Alcohol("고든", "양주", 3, 4.9,  R.drawable.src_godons, "태그"),
            Alcohol("모스카토 다스티", "와인", 4, 4.2,  R.drawable.src_wine, "산미")
        ).also { totalAlcoholData = it }
    }

    fun deleteComb(userId: Long, combId: Long){
        viewModelScope.launch {
            deleteCombUseCase(userId, combId)
                .catch {  }
                .collect ()
        }
    }

    fun postComb(userId: Long, combId: Long){
        viewModelScope.launch {
            postCombUseCase(userId, combId)
                .catch {  }
                .collect()
        }
    }


    fun setTypeAlcohol(type: Int){
        if (type == 0){
            if (searchKeyword == null)
                _alcoholData.value = totalAlcoholData
            searchKeyword?.let {
                _alcoholData.value = totalAlcoholData.filter { a ->
                    a.name.contains(it)
                }
            }
        }
        else {
            if (searchKeyword == null)
                _alcoholData.value = totalAlcoholData.filter { alcohol ->  alcohol.typeNum == type}
            searchKeyword?.let {
                _alcoholData.value = totalAlcoholData.filter {
                    alcohol -> alcohol.typeNum == type && alcohol.name.contains(it)
                }
            }
        }
    }

    fun search(keyword: String){
        searchKeyword = keyword
        _alcoholData.value = totalAlcoholData.filter { alcohol -> alcohol.name.contains(keyword) }
        _combData.value = totalCombData.filter { comb -> comb.drinkname.contains(keyword) }
    }

    fun searchClose(){
        searchKeyword = null
        _alcoholData.value = totalAlcoholData
        _combData.value = totalCombData
    }
}