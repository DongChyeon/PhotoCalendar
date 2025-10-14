package com.dongchyeon.app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform