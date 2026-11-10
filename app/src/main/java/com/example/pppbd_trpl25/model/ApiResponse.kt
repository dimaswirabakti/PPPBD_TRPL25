package com.example.pppbd_trpl25.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("message")
    val message: String?
)