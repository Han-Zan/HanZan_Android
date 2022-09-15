package com.kud.hanzan.domain.utils.error

enum class ErrorType {
    NETWORK,    // IO
    TIMEOUT,    // Socket
    UNKNOWN,    // Something Else
    SESSION_EXPIRED // Session 만료
}
