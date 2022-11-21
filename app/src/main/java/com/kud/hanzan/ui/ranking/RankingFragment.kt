package com.kud.hanzan.ui.ranking

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.widget.ListPopupWindow
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kud.hanzan.HanZanApplication
import com.kud.hanzan.R
import com.kud.hanzan.adapter.ranking.RankingRVAdapter
import com.kud.hanzan.databinding.FragmentRankingBinding
import com.kud.hanzan.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RankingFragment : BaseFragment<FragmentRankingBinding>(R.layout.fragment_ranking) {
    private val viewModel by viewModels<RankingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
    }

    private var userId: Long = -1
    private fun initView() {
        userId = HanZanApplication.spfManager.getUserIdx()
        viewModel.listAll(userId)
        observe()
    }

    private fun initListener() {
        with(binding) {
            val listDrinkPopupWindow = ListPopupWindow(requireContext(), null, com.google.android.material.R.attr.listPopupWindowStyle)
            listDrinkPopupWindow.apply {
                anchorView = rankingSortDrinkBtn
                val items = listOf("  전체  ", "  소주  ", "  맥주  ", "  양주  ", "  와인  ", "  기타  ")
                val adapter = ArrayAdapter(requireContext(), R.layout.list_popup_like_sort, items)
                setAdapter(adapter)
                setOnItemClickListener { _, _, position, _ ->
                    rankingSortDrinkBtn.text = items[position]
                    viewModel.chooseDrinkType(position)
                    // 팝업 닫기
                    dismiss()
                }
            }

            rankingSortDrinkBtn.setOnClickListener { listDrinkPopupWindow.show() }

            val listFoodPopupWindow = ListPopupWindow(requireContext(), null, com.google.android.material.R.attr.listPopupWindowStyle)
            listFoodPopupWindow.apply {
                anchorView = rankingSortFoodBtn
                val items = listOf("  전체  ", "  육류  ", "  생선  ", "  튀김  ", " 탕/찌개 ", "마른 안주", "과일/채소", " 디저트 ", "  기타  ")
                val adapter = ArrayAdapter(requireContext(), R.layout.list_popup_like_sort, items)
                setAdapter(adapter)
                setOnItemClickListener { _, _, position, _ ->
                    rankingSortFoodBtn.text = items[position]
                    viewModel.chooseFoodType(position)
                    // 팝업 닫기
                    dismiss()
                }
            }

            rankingSortFoodBtn.setOnClickListener { listFoodPopupWindow.show() }

            rankingListRv.apply {
                adapter = RankingRVAdapter().apply {
                    setListener(object : RankingRVAdapter.Listener{
                        override fun onClick(combId: Long) {
                        }

                        override fun onDelete(combId: Long) {
                            viewModel.deleteCombLike(userId, combId)
                        }

                        override fun onPost(combId: Long) {
                            viewModel.postCombLike(userId, combId)
                        }
                    })
                }
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

    private fun observe() {
        viewModel.totalLiveData.observe(viewLifecycleOwner) {
            (binding.rankingListRv.adapter as RankingRVAdapter).setData(it)
        }
        viewModel.listLiveData.observe(viewLifecycleOwner) {
            (binding.rankingListRv.adapter as RankingRVAdapter).setData(it)
        }
        viewModel.postPrefLiveData.observe(viewLifecycleOwner) {

        }
        viewModel.deletePrefLiveData.observe(viewLifecycleOwner) {

        }
    }
}