package com.example.kotlin_final

import android.R.attr.dial
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_final.adapters.FriendsAdapter
import com.example.kotlin_final.models.FriendsResponse
import com.example.kotlin_final.network.Repository
import com.example.kotlin_final.utils.mSharedPreferences
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_friends.*
import kotlinx.android.synthetic.main.content_friends.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response


class FriendsActivity: AppCompatActivity(), FriendsAdapter.FriendsHolder.OnAdapterListener {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: FriendsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)

        adapter = FriendsAdapter(ArrayList(), this)
        linearLayoutManager = LinearLayoutManager(this)
        friendsRecyclerView.layoutManager = linearLayoutManager
        friendsRecyclerView.adapter = adapter
        callService()
        mostrarUSer()

    }

    private fun callService() {
        val service = Repository.RetrofitRepository.getService()
        GlobalScope.launch(Dispatchers.IO) {
            val response: Response<List<FriendsResponse>> = service.getUsers()
            withContext(Dispatchers.Main) {

                try {
                    if (response.isSuccessful) {
                        val friends: List<FriendsResponse>? = response.body()

                        if (friends != null)
                        {
                            updateInfo(friends)
                            gsonGuardar(friends)
                        }
                    } else {
                        Toast.makeText(
                            this@FriendsActivity,
                            "Error ${response.code()}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: HttpException) {
                    Toast.makeText(this@FriendsActivity, "Error ${e.message}", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }
    private fun updateInfo(list: List<FriendsResponse>) {
        adapter.updateList(list)
    }

    override fun onItemClickListener(item: FriendsResponse) {
        TODO("Not yet implemented")
        Log.d(" click", "${item}")
    }

fun gsonGuardar(itemFriends:  List<FriendsResponse>)
{
    val mShared = mSharedPreferences(this)

    for (item in itemFriends) {
        val friends: FriendsResponse = FriendsResponse(
            item.id.toString(),
            item.username.toString(),
            item.name.toString(),
            item.lastname.toString(),
            item.image.toString(),
            item.occupation.toString(),
            item.age.toString(),
            item.email.toString(),
            item.location.toString(),
            item.phone.toString(),
            item.social.toString()
        )
        val gsonFriends = Gson().toJson(friends, FriendsResponse::class.java)
        mShared.put("friends", gsonFriends)
        mShared.save()
    }
}
    fun mostrarUSer()
    {
        val mShared  =  mSharedPreferences(this)
        val friendsString : String ? =mShared.getKey("friends")
        if(friendsString != null)
        {
            val userfriends : FriendsResponse = Gson().fromJson(friendsString, FriendsResponse::class.java)
            Log.d(" name", "${userfriends.name}")
            if(userfriends.name != null)
            {
                Toast.makeText(this@FriendsActivity, "hola ${userfriends.phone}", Toast.LENGTH_LONG).show()
                /* tv_friends_name.setOnClickListener {view ->
                    val phoneNo: String = tv_friends_phone.getText().toString()
                    Toast.makeText(this@FriendsActivity, "hola ${phoneNo}", Toast.LENGTH_LONG).show()
                    startActivity(Intent(Intent.ACTION_CALL, Uri.parse(phoneNo)))
                    }*/
            }
        }

    }





}
