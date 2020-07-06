package com.example.kotlin_final

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_final.adapters.CommentAdapter
import com.example.kotlin_final.models.Comment
import com.example.kotlin_final.models.PostReponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_comments.*

class CommentsActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)

        adapter = CommentAdapter(ArrayList(), this)
        linearLayoutManager = LinearLayoutManager(this)

        commentRecyclerView.layoutManager= linearLayoutManager
        commentRecyclerView.adapter = adapter


        var gson = intent.getStringExtra("post")

        val post:PostReponse = Gson().fromJson(gson, PostReponse::class.java)

      //  updateInfo(post.comment)

    }
    private fun updateInfo(list: List<Comment>) {
        adapter.updateList(list)
    }

}