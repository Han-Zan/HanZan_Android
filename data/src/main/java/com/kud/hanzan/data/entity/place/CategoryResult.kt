package com.kud.hanzan.data.entity.place

data class CategoryResult(
    val documents: List<CategoryDocument>,
    val meta: CategoryMeta
)

data class CategoryMeta(
    val is_end: Boolean,
    val pageable_count: Int,
    val same_name: Any,
    val total_count: Int
)

data class CategoryDocument(
    val address_name: String,
    val category_group_code: String,
    val category_group_name: String,
    val category_name: String,
    val distance: String,
    val id: String,
    val phone: String,
    val place_name: String,
    val place_url: String,
    val road_address_name: String,
    val x: String,
    val y: String
)