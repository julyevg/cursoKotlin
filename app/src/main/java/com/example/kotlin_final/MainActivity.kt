package com.example.kotlin_final

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.example.kotlin_final.models.UserResponse
import com.example.kotlin_final.network.Repository
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.content_activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.nio.file.attribute.UserDefinedFileAttributeView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))


        callService()

        freed.setOnClickListener{view ->
            val intento1 = Intent(this, FreedActivity::class.java)
            startActivity(intento1)
        }
        friends.setOnClickListener{view ->
            val intento1 = Intent(this, FriendsActivity::class.java)
            startActivity(intento1)
        }
    }

    private fun callService() {

        val service = Repository.RetrofitRepository.getService()
        GlobalScope.launch(Dispatchers.IO) {
            val response =  service.getProfile()

            withContext(Dispatchers.Main) {
                try {
                    if(response.isSuccessful) {

                        val user : UserResponse?  = response.body()
                        if( user != null) updateInfo(user)
                    }else{
                        Toast.makeText(this@MainActivity, "Error ${response.code()}", Toast.LENGTH_LONG).show()
                    }
                }catch (e : HttpException) {
                    Toast.makeText(this@MainActivity, "Error ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun updateInfo(user: UserResponse) {
        if(user.image.isNotEmpty()){
            Picasso.get().load(user.image).into(profile_image)
        }

        profile_fullname.text = user.name + "" + user.lastname
        profile_email.text = user.email
        profile_years.text = user.age
        profile_location.text = user.location
        profile_occupation.text = user.occupation
        profile_likes.text = user.social.likes.toString()
        profile_posts.text = user.social.posts.toString()
        profile_shares.text = user.social.shares.toString()
        profile_friends.text = user.social.shares.toString()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onStart() {
        super.onStart()
        Log.d("Estado", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Estado", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Estado", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Estado", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Estado", "onDestroy")
    }
}