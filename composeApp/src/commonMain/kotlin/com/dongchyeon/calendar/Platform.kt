package com.dongchyeon.calendar

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform