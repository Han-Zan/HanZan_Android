package com.kud.hanzan.ui.map

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.kud.hanzan.R
import com.kud.hanzan.databinding.FragmentMapBinding
import com.kud.hanzan.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.daum.mf.map.api.CalloutBalloonAdapter
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>(R.layout.fragment_map) {
    private lateinit var mapView: MapView

    companion object{
        private const val TAG = "MapFragment"
    }

    private val viewModel by viewModels<MapViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMap()
        initListener()
    }

    // Todo : 권한 설정 추가
    private fun initMap(){
        mapView = MapView(activity)
        binding.kakaoMapView.addView(mapView)
        mapView.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading

        // 마커 추가
//        val marker = MapPOIItem()
//        marker.apply {
//            itemName = "밀짚모자이크"
//            mapPoint = MapPoint.mapPointWithGeoCoord(37.5044517, 126.9505820)
////            커스텀 이미지로 가능, 비트맵 이미지만, 벡터 X
////            markerType = MapPOIItem.MarkerType.CustomImage
////            customImageResourceId = R.drawable.비트맵이미지
////            selectedMarkerType = MapPOIItem.MarkerType.CustomImage
////            customSelectedImageResourceId = R.drawable.marker_red
////            isCustomImageAutoscale = false
////            setCustomImageAnchor(0.5f, 1.0f)
//            //블루핀, 옐로우핀, 레드핀도 가능
//            markerType = MapPOIItem.MarkerType.RedPin
//        }
//        mapView.addPOIItem(marker)
    }

    // Todo : 삭제 버튼 눌렀을 때 핀 제거 추가해보기
    // 화면 나갔다 오면 마커 없어지는 것 해결 노력
    private fun initListener(){
        with(binding){
            mapSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        lifecycleScope.launch{
                            viewModel.getKeyWordPlace(query).also {
                                withContext(Dispatchers.Main) {mapView.removeAllPOIItems()} }.collect{
                                // 내부 로직 구현
                                addMarker(it.placeName, it.x, it.y)
                            }
                        } }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
    }

    private fun addMarker(name: String,x: String, y: String){
        val marker = MapPOIItem()
        marker.apply {
            itemName = name
            mapPoint = MapPoint.mapPointWithGeoCoord(y.toDouble(), x.toDouble())
//            커스텀 이미지로 가능, 비트맵 이미지만, 벡터 X
//            markerType = MapPOIItem.MarkerType.CustomImage
//            customImageResourceId = R.drawable.비트맵이미지
//            selectedMarkerType = MapPOIItem.MarkerType.CustomImage
//            customSelectedImageResourceId = R.drawable.marker_red
//            isCustomImageAutoscale = false
//            setCustomImageAnchor(0.5f, 1.0f)
            //블루핀, 옐로우핀, 레드핀도 가능
            markerType = MapPOIItem.MarkerType.RedPin
        }
        mapView.addPOIItem(marker).also { Log.e(TAG, name) }
    }

    class CustomBalloonAdapter(): CalloutBalloonAdapter{
        // 마커 클릭시 나오는 말풍선
        override fun getCalloutBalloon(p0: MapPOIItem?): View {
            TODO("Not yet implemented")
        }

        // 말풍선 클릭시 event
        override fun getPressedCalloutBalloon(p0: MapPOIItem?): View {
            TODO("Not yet implemented")
        }

    }
}