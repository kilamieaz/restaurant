package com.kilamieaz.restaurant.model

import com.google.gson.annotations.SerializedName

data class Success(
    @SerializedName("success") var success : ModelUser? = null
)

data class ModelUser(
    @SerializedName("id") var id : Int? = null,
    @SerializedName("role_id") var role_id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("handphone") var handphone: String? = null,
    @SerializedName("photo") var photo: String? = null,
    @SerializedName("role") var role: ModelRole? = null
)
data class ModelRole(
    @SerializedName("id") var id : Int? = null,
    @SerializedName("name") var name : String? = null,
    @SerializedName("description") var description : String? = null
)