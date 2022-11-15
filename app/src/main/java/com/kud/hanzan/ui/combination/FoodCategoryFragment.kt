package com.kud.hanzan.ui.combination

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.kud.hanzan.R
import com.kud.hanzan.databinding.FragmentFoodCategoryBinding
import com.kud.hanzan.utils.base.BaseFragment

class FoodCategoryFragment : BaseFragment<FragmentFoodCategoryBinding>(R.layout.fragment_food_category) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener() {
        with(binding) {
            foodCategoryMeatBtn.setOnClickListener {
                val action = FoodCategoryFragmentDirections.actionFoodCategoryFragmentToFoodListFragment(0)
                findNavController().navigate(action)
            }
            foodCategoryFishBtn.setOnClickListener {
                val action = FoodCategoryFragmentDirections.actionFoodCategoryFragmentToFoodListFragment(1)
                findNavController().navigate(action)
            }
            foodCategoryFriesBtn.setOnClickListener {
                val action = FoodCategoryFragmentDirections.actionFoodCategoryFragmentToFoodListFragment(2)
                findNavController().navigate(action)
            }
            foodCategorySoupBtn.setOnClickListener {
                val action = FoodCategoryFragmentDirections.actionFoodCategoryFragmentToFoodListFragment(3)
                findNavController().navigate(action)
            }
            foodCategoryDryBtn.setOnClickListener {
                val action = FoodCategoryFragmentDirections.actionFoodCategoryFragmentToFoodListFragment(4)
                findNavController().navigate(action)
            }
            foodCategoryFruitBtn.setOnClickListener {
                val action = FoodCategoryFragmentDirections.actionFoodCategoryFragmentToFoodListFragment(5)
                findNavController().navigate(action)
            }
            foodCategoryDessertBtn.setOnClickListener {
                val action = FoodCategoryFragmentDirections.actionFoodCategoryFragmentToFoodListFragment(6)
                findNavController().navigate(action)
            }
            foodCategoryEtcBtn.setOnClickListener {
                val action = FoodCategoryFragmentDirections.actionFoodCategoryFragmentToFoodListFragment(7)
                findNavController().navigate(action)
            }

            foodCategoryToolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}