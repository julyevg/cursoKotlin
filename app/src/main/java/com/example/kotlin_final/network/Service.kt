package com.example.kotlin_final.network

import com.example.kotlin_final.models.FriendsResponse
import com.example.kotlin_final.models.PostReponse
import com.example.kotlin_final.models.UserResponse
import retrofit2.Response
import retrofit2.http.GET

interface Service {
    @GET("profile")
    suspend fun getProfile() :Response<UserResponse>
    @GET("posts")
    suspend fun getPosts(): Response<List<PostReponse>>
    @GET("users")
    suspend fun getUsers(): Response<List<FriendsResponse>>
}