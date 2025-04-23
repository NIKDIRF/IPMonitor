package com.example.ipmonitor.model

import com.google.gson.annotations.SerializedName

data class IpResponse(
    @SerializedName("myip")
    val myIp: String
)