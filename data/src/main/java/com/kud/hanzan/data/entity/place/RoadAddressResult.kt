package com.kud.hanzan.data.entity.place

data class RoadAddressResult(
    val documents: List<Document>,
)

data class Document(
    val address: Address
)

data class Address(
    val address_name: String,
    val region_1depth_name: String,
    val region_2depth_name: String,
    val region_3depth_name: String,
    val mountain_yn: String,
    val main_address_no: String,
    val sub_address_no: String
)