package com.kud.hanzan.ui.combination

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import androidx.appcompat.widget.ListPopupWindow
import com.kud.hanzan.R
import com.kud.hanzan.databinding.FragmentFoodCategoryBinding
import com.kud.hanzan.databinding.FragmentFoodListBinding
import com.kud.hanzan.utils.base.BaseFragment

class FoodListFragment : BaseFragment<FragmentFoodListBinding>(R.layout.fragment_food_list) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
    }

    private fun initView() {

    }

    private fun initListener() {
        with(binding){
            val listAlcoholPopupWindow = ListPopupWindow(requireContext(), null, com.google.android.material.R.attr.listPopupWindowStyle)
            listAlcoholPopupWindow.apply {
                anchorView = foodListStyleBtn
                val items = listOf(" 소고기 ", " 돼지고기 ", " 닭고기 ", " 회/구이 ", " 튀김류 ", " 파스타 ", " 튀김류 ")
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
                add("소주")
                add("맥주")
                add("양주")
                add("와인")
                add("기타")
            }
            popupMenu.setOnMenuItemClickListener {
                foodListStyleBtn.text = it.title
                true
            }

            foodListStyleBtn.setOnClickListener { listAlcoholPopupWindow.show() }
        }
    }
}