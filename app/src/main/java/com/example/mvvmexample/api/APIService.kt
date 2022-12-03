package com.example.mvvmexample.api
import com.example.mvvmexample.model.InterestingActivity
import retrofit2.Response
import retrofit2.http.GET

interface APIService {
    @GET("activity")
    suspend fun getActivity(): Response<InterestingActivity>
}