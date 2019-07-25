package com.kilamieaz.restaurant.model

import com.google.gson.annotations.SerializedName

data class ModelMember(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("role_id") var role_id: Int? =null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("handphone") var handphone: String? = null,
    @SerializedName("photo") var photo: String? = null
)