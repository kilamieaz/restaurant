package com.kilamieaz.restaurant.model

import com.google.gson.annotations.SerializedName

data class ModelLogin(
    @SerializedName("success") var success: ModelSuccess? = null
)