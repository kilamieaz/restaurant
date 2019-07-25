package com.kilamieaz.restaurant.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ModelCategory(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("description") var description: String? = null
) : Serializable