package com.kud.hanzan.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import com.kud.hanzan.R
import com.kud.hanzan.adapter.MapStoreRVAdapter
import com.kud.hanzan.databinding.FragmentMapBinding
import com.kud.hanzan.domain.model.Store
import com.kud.hanzan.utils.base.BaseFragment
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import net.daum.mf.map.api.CalloutBalloonAdapter
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView


@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>(R.layout.fragment_map), MapView.MapViewEventListener {
    private lateinit var fusedLocationProviderClient : FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var mapView: MapView


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
        setRVData()
    }

    override fun onResume() {
        super.onResume()
        binding.mapBottomLayout.maxHeight = (binding.kakaoMapView.height * 0.96).toInt()

        binding.isPickupShown = binding.mapPickupNearCb.isChecked
        binding.isNearShown = binding.mapPickupNearCb.isChecked
    }

    private fun initView(){
        // 맵뷰 초기화
        mapView = MapView(activity)
        binding.kakaoMapView.addView(mapView)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        binding.mapLayout.panelHeight = 700
        checkPermission()
        mapView.setMapViewEventListener(this)
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
        // 리사이클러뷰 이닛
        binding.mapBottomStoreRv.apply {
            adapter = MapStoreRVAdapter()
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    // 임시 데이터
    // Todo : 검색결과로 변경
    private fun setRVData(){
        val tempList = ArrayList<Store>()
        tempList.apply {
            add(Store("밀짚모자", "420m", "도보 3분"))
            add(Store("장독대", "850m", "도보 15분"))
            add(Store("제일주당", "300m", "도보 3분"))
            add(Store("맥주창고", "700m", "도보 13분"))
            add(Store("호랑이 술상", "400m", "도보 5분"))
        }
        (binding.mapBottomStoreRv.adapter as MapStoreRVAdapter).setData(tempList)
    }

    // Todo : 삭제 버튼 눌렀을 때 핀 제거 추가해보기
    private fun initListener(){
        with(binding){
            mapCurrentPosIv.setOnClickListener {
                setCurrentLocation() }
            // 체크박스 리스너
            mapPickupAvailCb.setOnClickListener {
                isPickupShown = !isPickupShown!!
            }

            mapPickupNearCb.setOnClickListener {
                isNearShown = !isNearShown!!
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

            mapLayout.addPanelSlideListener(PanelEventListener())
            // 패널 닫기
            mapListFab.setOnClickListener { mapLayout.panelState = SlidingUpPanelLayout.PanelState.EXPANDED }
            mapMapFab.setOnClickListener { mapLayout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED }
        }
    }

    private fun observe(){
        binding.lifecycleOwner = this
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.placeInfo.collectLatest {
                    state -> when(state){
                        is PlaceUiState.Success -> state.placeList.also { mapView.removeAllPOIItems() }.forEach { addMarker(it.placeName, it.x, it.y) }
                        is PlaceUiState.Error -> Toast.makeText(context, state.exception.toString(), Toast.LENGTH_SHORT).show()
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
            Toast.makeText(requireContext(), "권한을 허용해주세요", Toast.LENGTH_SHORT).show()
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
            return
        }
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(location.latitude, location.longitude), false)

                viewModel.setRoadAddress(location.longitude.toString(), location.latitude.toString())
            }
        }
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
        } else{
            mapView.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeadingWithoutMapMoving
            setCurrentLocation()
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
                    mapView.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeadingWithoutMapMoving
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
                    fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                        location?.let {
                            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(location.latitude, location.longitude), false)
                        }
                    }
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

    override fun onStop() {
        super.onStop()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    // Todo : 터치 잠금 추가할지 고민
    inner class PanelEventListener : SlidingUpPanelLayout.PanelSlideListener{
        // 패널 슬라이드 중일때
        override fun onPanelSlide(panel: View?, slideOffset: Float) {
        }

        // 패널 상태 변했을 때
        override fun onPanelStateChanged(
            panel: View?,
            previousState: SlidingUpPanelLayout.PanelState?,
            newState: SlidingUpPanelLayout.PanelState?
        ) {
            if (previousState == SlidingUpPanelLayout.PanelState.DRAGGING && newState == SlidingUpPanelLayout.PanelState.COLLAPSED)
                binding.mapBottomLayout.maxHeight = (binding.kakaoMapView.height * 0.963).toInt()
            when (newState) {
                SlidingUpPanelLayout.PanelState.EXPANDED, SlidingUpPanelLayout.PanelState.COLLAPSED -> {
                    binding.mapLayout.panelHeight = 0
//                    binding.mapBottomSlideIv.visibility = View.INVISIBLE
                }
                else -> {
//                    binding.mapBottomSlideIv.visibility = View.VISIBLE
                }
            }
        }

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
    override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {
        // 도로명 주소 알아오기
        p0?.let{ p1?.let { viewModel.setRoadAddress(it.mapPointGeoCoord.longitude.toString(), it.mapPointGeoCoord.latitude.toString()) }}
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
}