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
                val action = FoodCategoryFragmentDirections.actionFoodCategoryFragmentToFoodListFragment()
                findNavController().navigate(action)
            }
            foodCategoryFishBtn.setOnClickListener {
                val action = FoodCategoryFragmentDirections.actionFoodCategoryFragmentToFoodListFragment()
                findNavController().navigate(action)
            }
            foodCategoryFriesBtn.setOnClickListener {
                val action = FoodCategoryFragmentDirections.actionFoodCategoryFragmentToFoodListFragment()
                findNavController().navigate(action)
            }
            foodCategorySoupBtn.setOnClickListener {
                val action = FoodCategoryFragmentDirections.actionFoodCategoryFragmentToFoodListFragment()
                findNavController().navigate(action)
            }
            foodCategoryDryBtn.setOnClickListener {
                val action = FoodCategoryFragmentDirections.actionFoodCategoryFragmentToFoodListFragment()
                findNavController().navigate(action)
            }
            foodCategoryFruitBtn.setOnClickListener {
                val action = FoodCategoryFragmentDirections.actionFoodCategoryFragmentToFoodListFragment()
                findNavController().navigate(action)
            }
            foodCategoryDessertBtn.setOnClickListener {
                val action = FoodCategoryFragmentDirections.actionFoodCategoryFragmentToFoodListFragment()
                findNavController().navigate(action)
            }
            foodCategoryEtcBtn.setOnClickListener {
                val action = FoodCategoryFragmentDirections.actionFoodCategoryFragmentToFoodListFragment()
                findNavController().navigate(action)
            }

            foodCategoryToolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}