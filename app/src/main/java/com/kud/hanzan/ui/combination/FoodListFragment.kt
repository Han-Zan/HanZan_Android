package com.kud.hanzan.ui.combination

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.widget.ListPopupWindow
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.kud.hanzan.R
import com.kud.hanzan.adapter.FoodRVAdapter
import com.kud.hanzan.databinding.FragmentFoodListBinding
import com.kud.hanzan.domain.model.Food
import com.kud.hanzan.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodListFragment : BaseFragment<FragmentFoodListBinding>(R.layout.fragment_food_list) {
    private val viewModel by viewModels<FoodListViewModel>()
    private val styleArgs by navArgs<FoodListFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
    }

    private fun initView() {
        viewModel.getAllFood(styleArgs.style)
        observe()
    }

    private fun initListener() {
        with(binding){
            val listFoodPopupWindow = ListPopupWindow(requireContext(), null, com.google.android.material.R.attr.listPopupWindowStyle)
            listFoodPopupWindow.apply {
                anchorView = foodListStyleBtn
                val items = listOf("  육류  ", "  생선  ", "  튀김  ", " 탕/찌개 ", "마른 안주", "과일/채소", " 디저트 ", "  기타  ")
                val adapter = ArrayAdapter(requireContext(), R.layout.list_popup_like_sort, items)
                setAdapter(adapter)
                foodListStyleBtn.text = items[styleArgs.style]
                setOnItemClickListener { _, _, position, _ ->
                    foodListStyleBtn.text = items[position]
                    viewModel.chooseFoodType(position)
                    // 팝업 닫기
                    dismiss()
                }
            }

            foodListStyleBtn.setOnClickListener { listFoodPopupWindow.show() }

            foodListRv.apply {
                adapter = FoodRVAdapter().apply {
                    setListener(object : FoodRVAdapter.Listener{
                        override fun onSelect(food: Food) {
                            food.let {
                                Log.e(TAG, it.toString())
                                val action = FoodListFragmentDirections.actionFoodListFragmentToCombinationFragment(null, it)
                                findNavController().navigate(action)
                            }
                        }
                    })
                }
                layoutManager = LinearLayoutManager(context)
            }

            foodListToolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun observe(){
        viewModel.foodLiveData.observe(viewLifecycleOwner) {
            Log.e("Hello", it.toString())
            (binding.foodListRv.adapter as FoodRVAdapter).setData(it)
        }
    }
}