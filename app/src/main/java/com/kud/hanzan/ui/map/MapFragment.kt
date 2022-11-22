package com.kud.hanzan.ui.map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.kud.hanzan.R
import com.kud.hanzan.databinding.FragmentMapBinding
import com.kud.hanzan.databinding.ItemCustomBalloonBinding
import com.kud.hanzan.domain.model.map.Store
import com.kud.hanzan.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import net.daum.mf.map.api.CalloutBalloonAdapter
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView


@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>(R.layout.fragment_map), MapView.MapViewEventListener, MapView.POIItemEventListener {
    private lateinit var fusedLocationProviderClient : FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var mapView: MapView

    // setCurrentLocation이 처음 호출되었는지 여부 판단
    private var firstCalled = true

    // 현재 위치
    private var currentX: Double = 0.0
    private var currentY: Double = 0.0

    companion object{
        private const val TAG = "MapFragment"
        private const val multiplePermissionCode = 100
        private val requestPermissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    private val viewModel by viewModels<MapViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mapViewModel = viewModel
        initView()
        initLocationClient()
        initListener()
        observe()
    }

    private fun initView(){
        // 맵뷰 초기화
        mapView = MapView(activity)
        binding.kakaoMapView.addView(mapView)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        mapView.setMapViewEventListener(this)

        // Todo : 돌아 왔을 때 기존 포커스로 돌아오기
        // Todo : restore Marker
        val currentPos = viewModel.getCurrentPos()
        if (currentPos == null) checkPermission()
        else viewModel.getCurrentPos()?.let {
            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(it[1], it[0]), false)
        }
    }

    private fun initListener(){
        with(binding){
            mapCurrentPosIv.setOnClickListener {
                setCurrentLocation() }
            // 체크박스 리스너
//            mapPickupNearCb.setOnClickListener {
//                isNearShown = !isNearShown!!
//            }
            mapView.setPOIItemEventListener(this@MapFragment)
            // 버튼 리스너
            mapSearchBtn.setOnClickListener {
                viewModel.getCategoryPlace(mapView.mapCenterPoint.mapPointGeoCoord.longitude.toString(), mapView.mapCenterPoint.mapPointGeoCoord.latitude.toString(),
                    currentX, currentY)
                focusChanged = false
            }
            // 서치뷰 리스너
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
        binding.lifecycleOwner = this
        viewModel.placeSearchInfo.observe(viewLifecycleOwner){
            if (it is PlaceUiState.Success){
                if (it.placeList.isNotEmpty()) {
                    mapView.removeAllPOIItems()
                    //bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                    mapView.setMapCenterPoint(
                        MapPoint.mapPointWithGeoCoord(
                            it.placeList[0].y.toDouble(),
                            it.placeList[0].x.toDouble()
                        ), true
                    )
                    it.placeList.also { mapView.removeAllPOIItems() }
                        .forEach { p -> addMarker(p.name, p.x, p.y, p) }
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
//                    viewModel.placeSearchInfo.collectLatest { state ->
//                        when (state) {
//                            // Todo : 검색 결과 없을 때 대비
//                            is PlaceUiState.Success -> {
//                                if (state.placeList.isNotEmpty()) {
//                                    mapView.removeAllPOIItems()
//                                    //bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
//                                    mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(state.placeList[0].y.toDouble(), state.placeList[0].x.toDouble()), true)
//                                    state.placeList.also { mapView.removeAllPOIItems() }
//                                        .forEach { addMarker(it.name, it.x, it.y, it) }
//                                }
//                            }
//                            is PlaceUiState.Error -> Toast.makeText(
//                                context,
//                                state.exception.toString(),
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    }
                }
                launch {
                    viewModel.placeNearInfo.collectLatest {
                        mapView.removeAllPOIItems()
                        it.forEach { s -> addMarker(s.name, s.x, s.y, s) }
                    }
                }
            }
        }
    }

    private fun initLocationClient(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())

        val locationRequest = LocationRequest.create()?.apply {
            interval = 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest!!)
        val client = LocationServices.getSettingsClient(requireContext())
        client.checkLocationSettings(builder.build())

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    break
                }
            }
        }

        startLocationUpdates()
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        val locationRequest = LocationRequest.create()?.apply {
            interval = 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun setCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(context, "앱 설정에서 권한을 허용해주세요", Toast.LENGTH_SHORT).show()
            return
        }
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(location.latitude, location.longitude), false)


                currentX = location.longitude
                currentY = location.latitude

                viewModel.centerX = currentX
                viewModel.centerY = currentY
                // 지도 상단 주소 지정
//                viewModel.setRoadAddress(location.longitude.toString(), location.latitude.toString())

                if (firstCalled){
                    // 현재 주소 주위 카테고리 검색 결과 데이터
                    viewModel.getCategoryPlace(it.longitude.toString(), it.latitude.toString(), location.longitude, location.latitude)
                    firstCalled = false
                    binding.focusChanged = false
                }
            }
        }
    }

    private fun checkPermission(){
        var checker = false
        for (permission in requestPermissions){
            if (ActivityCompat.checkSelfPermission(requireActivity(), permission) != PackageManager.PERMISSION_GRANTED){
                // 권한 없다면 거절된 리스트에 추가
                checker = true
                break
            }
        }
        // 거절된 게 있다면 다시 물어보기
        if (checker){
        } else{
            mapView.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeadingWithoutMapMoving
            setCurrentLocation()

        }
//        locationPermissionRequest.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
//            Manifest.permission.ACCESS_COARSE_LOCATION))
    }

    private fun addMarker(name: String,x: String, y: String, store: Store){
        val marker = MapPOIItem()
        marker.apply {
            userObject = store
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

    override fun onStop() {
        super.onStop()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {
        // 도로명 주소 알아오기
        p0?.let{ p1?.let {
            viewModel.setRoadAddress(it.mapPointGeoCoord.longitude.toString(), it.mapPointGeoCoord.latitude.toString())
        }}
        binding.focusChanged = true
    }

    override fun onMapViewInitialized(p0: MapView?) {

    }

    override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {

    }

    override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {

    }

    override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {
    }

    override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?) {
        val store = p1?.userObject as Store

        val action = MapFragmentDirections.actionMapFragmentToStoreFragment(store)
        findNavController().navigate(action)
    }

    override fun onCalloutBalloonOfPOIItemTouched(
        p0: MapView?,
        p1: MapPOIItem?,
        p2: MapPOIItem.CalloutBalloonButtonType?
    ) {

    }

    override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) {
    }
}