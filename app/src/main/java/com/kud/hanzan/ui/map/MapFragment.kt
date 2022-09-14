package com.kud.hanzan.ui.map

import android.os.Bundle
import android.view.View
import com.kud.hanzan.R
import com.kud.hanzan.databinding.FragmentMapBinding
import com.kud.hanzan.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import net.daum.mf.map.api.MapView

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>(R.layout.fragment_map) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMap()
    }

    private fun initMap(){
        val mapView = MapView(activity)
        val mapViewContainer = binding.kakaoMapView
        mapViewContainer.addView(mapView)
    }
}