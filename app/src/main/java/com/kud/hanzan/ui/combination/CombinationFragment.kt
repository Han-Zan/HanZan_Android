package com.kud.hanzan.ui.combination

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kud.hanzan.R
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
            viewModel.setDrink(it)
        } ?: run {
            binding.drink = viewModel.drinkLiveData.value
        }
        combNavArgs.food?.let {
            viewModel.setFood(it)
        } ?: run {
            binding.food = viewModel.foodLiveData.value
        }
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
            if (binding.drink != null && binding.food != null) {
                binding.combinationNextBtn.visibility = View.VISIBLE
            }
        }
        viewModel.drinkLiveData.observe(viewLifecycleOwner) {
            binding.drink = viewModel.drinkLiveData.value
            if (binding.drink != null && binding.food != null) {
                binding.combinationNextBtn.visibility = View.VISIBLE
            }
        }
    }
}