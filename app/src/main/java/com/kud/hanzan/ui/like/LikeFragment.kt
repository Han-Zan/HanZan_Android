package com.kud.hanzan.ui.like

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.kud.hanzan.R
import com.kud.hanzan.adapter.like.LikeVPAdapter
import com.kud.hanzan.databinding.FragmentLikeBinding
import com.kud.hanzan.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LikeFragment : BaseFragment<FragmentLikeBinding>(R.layout.fragment_like) {
    private val viewModel by viewModels<LikeViewModel>()
    private val tabInfo = listOf("내가 찜한 조합", "내가 찜한 술")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){
        with(binding){
            likeTabVp.adapter = LikeVPAdapter(this@LikeFragment)
            likeTabVp.isUserInputEnabled = false
            TabLayoutMediator(likeTabTb, likeTabVp){
                tab, position -> tab.text = tabInfo[position]
            }.attach()
        }

        binding.likeToolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.search_menu -> {
                    val searchView = it.actionView as SearchView
                    searchView.queryHint = getString(R.string.like_search_hint)
                    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            query?.let{
                                str -> viewModel.search(str)
                            }
                            return true
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            if (newText.isNullOrEmpty()){
                                viewModel.searchClose()
                            }
                            return true
                        }
                    })
                    true
                }
                else -> false
            }
        }

    }


}