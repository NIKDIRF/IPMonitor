package com.example.ipmonitor.model

import retrofit2.http.GET

interface IpService {

    @GET("d4e2bt6jba6cmiekqmsv")
    suspend fun getIpAddress(): IpResponse
}