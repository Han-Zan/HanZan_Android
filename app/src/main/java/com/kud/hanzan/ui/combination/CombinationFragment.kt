package com.kud.hanzan.ui.combination

import android.content.ContentValues.TAG
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kud.hanzan.HanZanApplication
import com.kud.hanzan.R
import com.kud.hanzan.databinding.FragmentCombinationBinding
import com.kud.hanzan.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CombinationFragment : BaseFragment<FragmentCombinationBinding>(R.layout.fragment_combination){
    private val viewModel by activityViewModels<CombinationViewModel>()
    private val combNavArgs by navArgs<CombinationFragmentArgs>()

    private var userId: Long = -1
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
        observe()
    }

    private fun initView(){
        userId = HanZanApplication.spfManager.getUserIdx()

        isCombPrinted = false

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
        with(binding) {
            combinationDrinkBtn.setOnClickListener {
                val action =
                    CombinationFragmentDirections.actionCombinationFragmentToDrinkListFragment()
                findNavController().navigate(action)
            }
            combinationFoodBtn.setOnClickListener {
                val action =
                    CombinationFragmentDirections.actionCombinationFragmentToFoodCategoryFragment()
                findNavController().navigate(action)
            }
        }
        binding.combinationNextBtn.setOnClickListener {
            binding.combinationNextBtn.visibility = View.INVISIBLE
            isCombPrinted = true
            binding.drink?.let { drink ->
                binding.food?.let { food ->
                    viewModel.recommendation(drink, food, userId)
                }
            }
        }
        binding.combinationLikeCb.setOnClickListener {
            if (binding.combinationLikeCb.isChecked) {
                viewModel.postCombLike(userId, combIdx)
            } else {
                viewModel.deleteCombLike(userId, combIdx)
            }
        }
    }
    private var isCombPrinted = false

    private var combIdx: Long = -1
    private fun observe() {
        viewModel.foodLiveData.observe(viewLifecycleOwner) {
            binding.food = it
            if (binding.drink != null && binding.food != null) {
                binding.combinationNextBtn.visibility = View.VISIBLE
            }
        }
        viewModel.drinkLiveData.observe(viewLifecycleOwner) {
            binding.drink = it
            if (binding.drink != null && binding.food != null) {
                binding.combinationNextBtn.visibility = View.VISIBLE
            }
        }
        viewModel.combLiveData.observe(viewLifecycleOwner) {
            if (isCombPrinted) binding.combinationScoreCl.visibility = View.VISIBLE
            combIdx = it.combIdx
            binding.combinationScore.text = it.score.toString()
            binding.combinationLikeCb.isChecked = it.prefer
            binding.combinationRatingBar.rating = it.rating
        }
        viewModel.postPrefLiveData.observe(viewLifecycleOwner) {
            binding.combinationLikeCb.isChecked = true
        }
        viewModel.deletePrefLiveData.observe(viewLifecycleOwner) {
            binding.combinationLikeCb.isChecked = false
        }
    }
}