package com.kilamieaz.restaurant.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ModelMenu(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("category_id") var category_id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("price") var price: Int? = null,
    @SerializedName("stock") var stock: Int? = null,
    @SerializedName("photo") var photo: String? = null,
    @SerializedName("category") var category: ModelCategory? = null
) : Serializable