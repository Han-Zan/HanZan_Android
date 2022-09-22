package com.kud.hanzan.data.entity.place

data class RoadAddressResult(
    val documents: List<Document>
)

data class Document(
    val road_address: RoadAddress?
)

data class RoadAddress(
    val address_name: String,
    val region_1depth_name: String,
    val region_2depth_name: String,
    val region_3depth_name: String,
    val road_name: String,
    val mountain_yn: String,
    val main_building_no: String,
    val sub_building_no: String
)