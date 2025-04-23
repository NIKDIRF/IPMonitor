package com.example.ipmonitor.repository

import com.example.ipmonitor.model.IpService
import javax.inject.Inject

class IpRepository @Inject constructor(
    private val service: IpService
) {
    suspend fun getIpAddress(): String {
        return service.getIpAddress().myIp
    }
}