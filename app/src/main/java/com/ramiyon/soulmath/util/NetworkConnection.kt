package com.ramiyon.soulmath.util

import kotlin.concurrent.thread


fun isInternetConnection(): Boolean {
    var returnVal = false
    thread {
        returnVal = try {
            khttp.get("https://www.google.com/")
            true
        }catch (e:Exception){
            false
        }
    }.join()
    return returnVal
}