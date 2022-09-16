package com.kud.hanzan.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kud.hanzan.R
import com.kud.hanzan.databinding.FragmentMapBinding
import com.kud.hanzan.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import net.daum.mf.map.api.CalloutBalloonAdapter
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>(R.layout.fragment_map) {
    private lateinit var mapView: MapView

    companion object{
        private const val TAG = "MapFragment"
        private const val multiplePermissionCode = 100
        private val requestPermissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted.
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Only approximate location access granted.
            } else -> {
            // No location access granted.
        }
        }
    }

    private val viewModel by viewModels<MapViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        initMap()
        initListener()
        observe()
    }

    // Todo : 현재 위치 찾으려 할 때 권한 항상 check
    private fun initMap(){
        mapView = MapView(activity)
        binding.kakaoMapView.addView(mapView)

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

    private fun checkPermission(){
        val rejectedPermissions = ArrayList<String>()
        for (permission in requestPermissions){
            if (ActivityCompat.checkSelfPermission(requireActivity(), permission) != PackageManager.PERMISSION_GRANTED){
                // 권한 없다면 거절된 리스트에 추가
                rejectedPermissions.add(permission)
            }
        }
        // 거절된 게 있다면 다시 물어보기
        if (rejectedPermissions.isNotEmpty()){
            val array = arrayOfNulls<String>(rejectedPermissions.size)
            ActivityCompat.requestPermissions(requireActivity(), rejectedPermissions.toArray(array), multiplePermissionCode)
        }
//        locationPermissionRequest.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
//            Manifest.permission.ACCESS_COARSE_LOCATION))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            multiplePermissionCode -> {
                if ((grantResults.isNotEmpty()) && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                } else{
                    Toast.makeText(requireContext(), "권한을 허용해주세요.", Toast.LENGTH_SHORT).show()
                }
                return
            }
            else -> {
                Toast.makeText(requireContext(), "권한을 허용해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Todo : 삭제 버튼 눌렀을 때 핀 제거 추가해보기
    private fun initListener(){
        with(binding){
            mapSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        viewModel.getKeyWordPlace(it) }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
    }

    private fun observe(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.placeInfo.collectLatest {
                    state -> when(state){
                        is PlaceUiState.Success -> state.placeList.forEach { addMarker(it.placeName, it.x, it.y) }
                        is PlaceUiState.Error -> Toast.makeText(context, state.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
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
        mapView.addPOIItem(marker)
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