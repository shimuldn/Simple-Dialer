package com.simplemobiletools.dialer.models

data class TrueCallerResponse (
    val name: String,
    var carrier: String,
    var city: String,
    var spamType: String,
    var numReports: String,
    var status: String
)
