package com.example.pppbd_trpl25.network

import com.example.pppbd_trpl25.model.ApiResponse
import com.example.pppbd_trpl25.model.UserDetail
import com.example.pppbd_trpl25.model.UserRequest
import com.example.pppbd_trpl25.model.Users
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("employees")
    fun getAllUsers(): Call<Users>

    @GET("employee/{id}")
    fun getUserById(@Path("id") id: Int): Call<UserDetail>

    @POST("create")
    fun createUser(@Body body: UserRequest): Call<ApiResponse>

    @PUT("update/{id}")
    fun updateUser(@Path("id") id: Int, @Body body: UserRequest): Call<ApiResponse>

    @DELETE("delete/{id}")
    fun deleteUser(@Path("id") id: Int): Call<ApiResponse>
}