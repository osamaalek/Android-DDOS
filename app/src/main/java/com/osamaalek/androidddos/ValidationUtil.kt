package com.osamaalek.androidddos

import java.util.regex.Pattern

class ValidationUtil {

    companion object {
        var zeroTo255 = ("(\\d{1,2}|(0|1)\\" + "d{2}|2[0-4]\\d|25[0-5])")

        var regex = ("$zeroTo255\\.$zeroTo255\\.$zeroTo255\\.$zeroTo255")

        fun isValidIp(ip: String): Boolean {
            val pattern = Pattern.compile(regex)
            return pattern.matcher(ip).matches()
        }
    }
}