package com.example.mvvmexample.api
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper {
    companion object {
        fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://www.boredapi.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}