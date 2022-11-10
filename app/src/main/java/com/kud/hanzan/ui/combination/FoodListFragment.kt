package com.kud.hanzan.ui.combination

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import androidx.appcompat.widget.ListPopupWindow
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kud.hanzan.R
import com.kud.hanzan.adapter.DrinkRVAdapter
import com.kud.hanzan.adapter.FoodRVAdapter
import com.kud.hanzan.databinding.FragmentFoodListBinding
import com.kud.hanzan.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodListFragment : BaseFragment<FragmentFoodListBinding>(R.layout.fragment_food_list) {
    private val viewModel by viewModels<FoodListViewModel>()

    //private val styleArgs by navArgs<FoodListFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
        viewModel.getAllFood()
        observe()
    }

    private fun initView() {
        //val style = styleArgs.style
        binding.foodListRv.adapter = FoodRVAdapter()
        binding.foodListRv.layoutManager = LinearLayoutManager(context)
    }

    private fun initListener() {
        with(binding){
            val listAlcoholPopupWindow = ListPopupWindow(requireContext(), null, com.google.android.material.R.attr.listPopupWindowStyle)
            listAlcoholPopupWindow.apply {
                anchorView = foodListStyleBtn
                val items = listOf(" 육류 ", " 생선 ", " 튀김 ", " 탕/찌개 ", " 마른 안주 ", " 과일/채소 ", " 디저트 ", " 기타 ")
                val adapter = ArrayAdapter(requireContext(), R.layout.list_popup_like_sort, items)
                setAdapter(adapter)
                setOnItemClickListener { _, _, position, _ ->
                    foodListStyleBtn.text = items[position]
                    // 팝업 닫기
                    dismiss()
                }
            }

            // 임시 팝업 메뉴뉴
            val popupMenu = PopupMenu(context, foodListStyleBtn)
            popupMenu.menu.apply {
                add("육류")
                add("생선")
                add("튀김")
                add("탕/찌개")
                add("마른 안주")
                add("과일/채소")
                add("디저트")
                add("기타")
            }
            popupMenu.setOnMenuItemClickListener {
                foodListStyleBtn.text = it.title
                true
            }

            foodListStyleBtn.setOnClickListener { listAlcoholPopupWindow.show() }
        }
    }

    private fun observe(){
        viewModel.foodLiveData.observe(viewLifecycleOwner) {
            Log.e("Hello", it.toString())
            (binding.foodListRv.adapter as FoodRVAdapter).setData(it)
        }
    }
}