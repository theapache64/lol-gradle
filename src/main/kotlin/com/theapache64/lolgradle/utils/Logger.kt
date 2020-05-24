package com.theapache64.lolgradle.utils

/**
 * Created by theapache64 : May 24 Sun,2020 @ 12:26
 */
var IS_LOGGER_ENABLED = false
    set(value) {
        if (value) {
            println("Logging enabled...")
            field = value
        }
    }

fun log(msg: String) {
    if (IS_LOGGER_ENABLED) {
        println(msg)
    }
}