package com.kud.hanzan.vision

import android.util.Log
import kotlin.math.max
import kotlin.math.min

fun getTrimmedString(str: String) : String{
    // 영어, 한국어, 공백 제외 전부 제거
    val s = str.replace("[^a-zA-Z가-힣\\s]".toRegex(), "")
            // 나라 이름 제거
        .replace("France|French|Italy|Italia|Spain|프랑스|이태리|이탈리아".toRegex(), "")
        .replace("Champagne|샴페인|".toRegex(), "")
            // 중복 공백 제거
        .replace("\\s+".toRegex(), " ")
            // 맨앞 맨뒤 공백 제거
        .replace("^\\s+|\\s+$".toRegex(), "")
    Log.e("camera trim", s)
    return s
}

fun getLevenshteinDistance(x: String, y: String): Int {
    val m = x.length
    val n = y.length
    val T = Array(m + 1) { IntArray(n + 1) }
    for (i in 1..m) {
        T[i][0] = i
    }
    for (j in 1..n) {
        T[0][j] = j
    }
    var cost: Int
    for (i in 1..m) {
        for (j in 1..n) {
            cost = if (x[i - 1] == y[j - 1]) 0 else 1
            T[i][j] = min(min(T[i - 1][j] + 1, T[i][j - 1] + 1),
                T[i - 1][j - 1] + cost)
        }
    }
    return T[m][n]
}

fun findSimilarity(x: String?, y: String?): Double {
    require(!(x == null || y == null)) { "Strings should not be null" }

    val maxLength = max(x.length, y.length)
    return if (maxLength > 0) {
        (maxLength * 1.0 - getLevenshteinDistance(x, y)) / maxLength * 1.0
    } else 1.0
}

//
//fun levenshtein(lhsOriginal : CharSequence, rhs : CharSequence) : Int {
//    var lhs = lhsOriginal.trim()
//    lhs = lhs.replace("[^a-zA-Z가-힣]".toRegex(), "")
//    Log.e("home lhs trim", lhs.toString())
//    if(lhs == rhs) { return 0 }
//    if(lhs.isEmpty()) { return rhs.length }
//    if(rhs.isEmpty()) { return lhs.length }
//
//    val lhsLength = lhs.length + 1
//    val rhsLength = rhs.length + 1
//
//    var cost = Array(lhsLength) { it }
//    var newCost = Array(lhsLength) { 0 }
//
//    for (i in 1 until rhsLength) {
//        newCost[0] = i
//
//        for (j in 1 until lhsLength) {
//            val match = if(lhs[j - 1] == rhs[i - 1]) 0 else 1
//
//            val costReplace = cost[j - 1] + match
//            val costInsert = cost[j] + 1
//            val costDelete = newCost[j - 1] + 1
//
//            newCost[j] = min(min(costInsert, costDelete), costReplace)
//        }
//
//        val swap = cost
//        cost = newCost
//        newCost = swap
//    }
//
//    return cost[lhsLength - 1]
//}