package com.kud.hanzan.ui.combination

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kud.hanzan.R
import com.kud.hanzan.adapter.FoodRVAdapter
import com.kud.hanzan.databinding.FragmentCombinationBinding
import com.kud.hanzan.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CombinationFragment : BaseFragment<FragmentCombinationBinding>(R.layout.fragment_combination){
    private val viewModel by activityViewModels<CombinationViewModel>()
    private val combNavArgs by navArgs<CombinationFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
        observe()
    }

    private fun initView(){
        combNavArgs.drink?.let {
            Log.e(TAG, "Args.drink = ${combNavArgs.drink.toString()}")
            viewModel.setDrink(it)
        } ?: run {
            Log.e(TAG, "viewModel.drink = ${viewModel.drinkLiveData.value.toString()}")
            binding.drink = viewModel.drinkLiveData.value
        }
        combNavArgs.food?.let {
            Log.e(TAG, "Args.food = ${combNavArgs.food.toString()}")
            viewModel.setFood(it)
        } ?: run {
            Log.e(TAG, "viewModel.food = ${viewModel.foodLiveData.value.toString()}")
            binding.food = viewModel.foodLiveData.value
        }
        Log.e(TAG, "-------")
    }

    private fun initListener(){
        with(binding){
            combinationDrinkBtn.setOnClickListener {
                val action = CombinationFragmentDirections.actionCombinationFragmentToDrinkListFragment()
                findNavController().navigate(action)
            }
            combinationFoodBtn.setOnClickListener {
                val action = CombinationFragmentDirections.actionCombinationFragmentToFoodCategoryFragment()
                findNavController().navigate(action)
            }
        }
    }

    private fun observe() {
        viewModel.foodLiveData.observe(viewLifecycleOwner) {
            binding.food = viewModel.foodLiveData.value
        }
        viewModel.drinkLiveData.observe(viewLifecycleOwner) {
            binding.drink = viewModel.drinkLiveData.value
        }
    }
}