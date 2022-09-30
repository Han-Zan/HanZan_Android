package com.kud.hanzan.ui.category

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kud.hanzan.R
import com.kud.hanzan.adapter.category.CategoryMainRVAdapter
import com.kud.hanzan.adapter.category.CategorySubRVAdapter
import com.kud.hanzan.databinding.FragmentCategoryBinding
import com.kud.hanzan.domain.model.Category
import com.kud.hanzan.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>(R.layout.fragment_category) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
    }

    private fun initView(){
        val array = resources.getStringArray(R.array.main_category_name)
        binding.categoryMainRv.apply {
            adapter = CategoryMainRVAdapter(array.toList()).apply {
                setListener(object: CategoryMainRVAdapter.CategoryListener{
                    override fun onClick(position: Int) {
                        with(binding.categorySubRv.adapter as CategorySubRVAdapter) {
                            when (position) {
                                2 -> {
                                    // Todo : Api로 받아와야 함, 각 카테고리 상품별 갯수
                                    val array = resources.getStringArray(R.array.sub_category_liquor).toList()
                                    // data 카테고리화
                                    val tempList = ArrayList<Category>()
                                    for (a in array){
                                        tempList.add(Category(a, 123))
                                    }
                                    setData(tempList)
                                }
                            }
                        }
                    }
                })
            }
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
        binding.categorySubRv.apply {
            adapter = CategorySubRVAdapter()
            layoutManager = GridLayoutManager(context, 2)
        }

    }

    private fun initListener(){
        binding.categorySearchIv.setOnClickListener {
            val action = CategoryFragmentDirections.actionCategoryFragmentToSearchActivity()
            findNavController().navigate(action)
        }
    }
}