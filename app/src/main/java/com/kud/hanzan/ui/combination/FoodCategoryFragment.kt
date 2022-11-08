package com.kud.hanzan.ui.combination

import android.os.Bundle
import android.view.View
import com.kud.hanzan.R
import com.kud.hanzan.databinding.FragmentFoodCategoryBinding
import com.kud.hanzan.utils.base.BaseFragment

class FoodCategoryFragment : BaseFragment<FragmentFoodCategoryBinding>(R.layout.fragment_food_category) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
    }

    private fun initView() {

    }

    private fun initListener() {
        with(binding) {
            foodCategoryBeefBtn.setOnClickListener {

            }
            foodCategoryPorkBtn.setOnClickListener {

            }
            foodCategoryChickenBtn.setOnClickListener {

            }
            foodCategoryFishBtn.setOnClickListener {

            }
            foodCategoryFriesBtn.setOnClickListener {

            }
            foodCategoryPastaBtn.setOnClickListener {

            }
            foodCategorySpicyBtn.setOnClickListener {

            }
            foodCategoryFruitBtn.setOnClickListener {

            }
            foodCategorySaladBtn.setOnClickListener {

            }
            foodCategoryDessertBtn.setOnClickListener {

            }
        }
    }


}