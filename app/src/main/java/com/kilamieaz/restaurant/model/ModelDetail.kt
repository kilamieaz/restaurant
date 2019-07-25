package com.kilamieaz.restaurant.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ModelDetail(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("order_id") var order_id: Int? = null,
    @SerializedName("menu_id") var menu_id: Int? = null,
    @SerializedName("table_id") var table_id: Int? = null,
    @SerializedName("chef_id") var chef_id: Int? = null,
    @SerializedName("waiter_id") var waiter_id: Int? = null,
    @SerializedName("quantity") var quantity: Int? = null,
    @SerializedName("sub_total") var sub_total: Int? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("status") var status: Int? = null,
    @SerializedName("menu") var menu: ModelMenu? = null,
    @SerializedName("table") var table: ModelTable? = null
) : Serializable