package com.example.kotlin_final

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_final.adapters.PostAdapter
import com.example.kotlin_final.models.Comment
import com.example.kotlin_final.models.FriendsResponse
import com.example.kotlin_final.models.PostReponse
import com.example.kotlin_final.network.Repository
import com.example.kotlin_final.utils.mSharedPreferences
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_freed.*
import kotlinx.android.synthetic.main.content_freed.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response

class FreedActivity : AppCompatActivity(), PostAdapter.PostHolder.OnAdapterListener {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: PostAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_freed)

        adapter = PostAdapter(ArrayList(), this)
        linearLayoutManager = LinearLayoutManager(this)
        postRecyclerView.layoutManager= linearLayoutManager
        postRecyclerView.adapter = adapter
        callService()

    }

    private fun callService() {
        val service = Repository.RetrofitRepository.getService()
        GlobalScope.launch(Dispatchers.IO) {
            val response : Response<List<PostReponse>> =  service.getPosts()
            withContext(Dispatchers.Main) {
                try {
                    if(response.isSuccessful) {
                        val posts : List<PostReponse>?  = response.body()
                        if( posts != null) updateInfo(posts)

                    }else{
                        Toast.makeText(this@FreedActivity, "Error ${response.code()}", Toast.LENGTH_LONG).show()
                    }
                }catch (e : HttpException) {
                    Toast.makeText(this@FreedActivity, "Error ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    private fun updateInfo(list: List<PostReponse>) {
        adapter.updateList(list)
    }

    override fun onItemClickListener(item: PostReponse) {

    }

    fun gsonGuardar(itemFreed:  List<PostReponse>)
    {
        val mShared = mSharedPreferences(this)

        for (item in itemFreed)
        {
            /*    val comment: Comment = Comment(
                 /*   item.comment.username.toString(),
                    item.comment.user_image.toString(),
                    item.comment.comment.toString()*/
                )
                val gsonComment = Gson().toJson(comment, FriendsResponse::class.java)
                mShared.put("friends", gsonComment)
                mShared.save()*/
        }
    }
}