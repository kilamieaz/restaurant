package com.kilamieaz.restaurant.utils

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit



class ApiClient(){

    var retrofit : Retrofit? = null

    fun getClient() : Retrofit {

        if (retrofit == null) {

            retrofit = Retrofit.Builder()
                .baseUrl("http://10.10.4.25:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit!!
        } else {
            return retrofit!!
        }
    }

    companion object{
        val storageUrl = "http://192.168.56.1:8000/storage/"
    }
}