package com.kilamieaz.restaurant.model

import com.google.gson.annotations.SerializedName

data class ModelSuccess(
    @SerializedName("token") var token: String? = null
)