package com.kilamieaz.restaurant.model

import com.google.gson.annotations.SerializedName

data class ModelTable(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("capacity") var capacity: Int? = null,
    @SerializedName("status") var status: Int? = null
)