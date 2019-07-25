package com.kilamieaz.restaurant.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ModelOrder (
    @SerializedName("id") var id: Int? = null,
    @SerializedName("cashier_id") var cashier_id: Int? = null,
    @SerializedName("member_id") var member_id: Int? = null,
    @SerializedName("order_code") var order_code: String? = null,
    @SerializedName("created_at") var created_at: String? = null,
    @SerializedName("detail_orders") var detail_orders: ArrayList<ModelDetail>? = null,
    @SerializedName("member") var member: ModelMember? = null
): Serializable