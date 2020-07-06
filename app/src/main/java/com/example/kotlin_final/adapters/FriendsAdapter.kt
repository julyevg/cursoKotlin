package com.example.kotlin_final.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_final.models.FriendsResponse
import com.squareup.picasso.Picasso
import com.example.kotlin_final.R
import kotlinx.android.synthetic.main.content_friends.view.*

class FriendsAdapter(private var data: List<FriendsResponse>, private val listener: FriendsHolder.OnAdapterListener) :
    RecyclerView.Adapter<FriendsAdapter.FriendsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsHolder {
        val inflatedView = parent.inflate(R.layout.content_friends, false)
        return FriendsHolder(inflatedView)
    }

    fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }

    fun updateList(postList: List<FriendsResponse>) {
        this.data = postList

        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: FriendsHolder, position: Int) {
        val friends: FriendsResponse = this.data[position]
        holder.itemView.tv_friends_name.text = friends.name + " " + friends.lastname
        holder.itemView.tv_friends_phone.text = friends.phone

        if(!friends.image.isBlank()) {
            Picasso.get()
                .load(friends.image)
                .into(holder.itemView.photo_friends)
        }

    }


    override fun getItemCount(): Int {
        return data.size
    }

    class FriendsHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (v != null) {
                Toast.makeText(v.context, "Item", Toast.LENGTH_SHORT).show()
            }
        }

        interface OnAdapterListener {

            fun onItemClickListener(item: FriendsResponse)
        }

    }

}