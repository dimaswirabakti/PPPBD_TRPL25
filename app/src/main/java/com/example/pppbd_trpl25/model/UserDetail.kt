package com.example.pppbd_trpl25.model

import com.google.gson.annotations.SerializedName

data class UserDetail(
    @SerializedName("data")
    val data: Data
)