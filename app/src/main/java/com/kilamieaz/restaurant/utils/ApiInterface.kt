package com.kilamieaz.restaurant.utils

import com.kilamieaz.restaurant.model.ModelDetail
import com.kilamieaz.restaurant.model.ModelLogin
import com.kilamieaz.restaurant.model.ModelOrder
import com.kilamieaz.restaurant.model.Success
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {


    @GET("chef")
    fun chefOrder() : Call<ArrayList<ModelOrder>>

    @GET("waiter")
    fun waitersOrder() : Call<ArrayList<ModelOrder>>

    @GET("order")
    fun orders() : Call<ArrayList<ModelOrder>>

    @FormUrlEncoded
    @POST("login")
    fun login(@Field("email") email : String, @Field("password") password : String) : Call<ModelLogin>

    @POST("details")
    fun detailsLogin(@Header("Authorization") auth: String,
                     @Header("Content-Type") contentType : String,
                     @Header("X-Requested-With") xRequest : String) : Call<Success>

//    @POST("order/{id}")
//    fun updateOrder(@Path("id") id: Int,
//                    @Body detail: ModelDetail) : Call<ModelDetail>

    @POST("order/{id}")
    fun updateOrder(@Path("id") id: Int,
                    @Query("status") status: Int,
                    @QueryMap map : HashMap<String, Int>) : Call<ModelDetail>
}