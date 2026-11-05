package com.example.pppbd_trpl25.network

import com.example.pppbd_trpl25.model.Users
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("employees")
    fun getAllUsers(): Call<Users>
}