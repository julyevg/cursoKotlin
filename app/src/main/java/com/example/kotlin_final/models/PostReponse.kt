package com.example.kotlin_final.models


class PostReponse(
    val id: Int,
    val user_id: Int,
    val username: String,
    val user_image: String,
    val body: String,
    val image: String,
    val likes: Int,
    val comment: Comment
)