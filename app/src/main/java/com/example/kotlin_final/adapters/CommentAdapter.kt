package com.example.kotlin_final.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_final.CommentsActivity
import com.example.kotlin_final.R

import com.example.kotlin_final.models.Comment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.content_comments.view.*


class CommentAdapter(private var data: List<Comment>, private val listener: CommentsActivity) :
    RecyclerView.Adapter<CommentAdapter.CommentHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
        val inflatedView = parent.inflate(R.layout.content_comments, false)
        return CommentHolder(inflatedView)
    }

    fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }

    fun updateList(commentList: List<Comment>) {
        this.data = commentList
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CommentHolder, position: Int) {
        val comm: Comment = this.data[position]
        holder.itemView.tv_comments
        holder.itemView.tv_comments.text = comm.comment

        if(!comm.user_image.isBlank()) {
            Picasso.get()
                .load(comm.user_image)
                .into(holder.itemView.photo_friends)
        }
       // holder.itemView.setOnClickListener { listener.onItemClickListener(comm) }
    }


    override fun getItemCount(): Int {
        return data.size
    }

    class CommentHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (v != null) {
                Toast.makeText(v.context, "Item", Toast.LENGTH_SHORT).show()
            }
        }

        interface OnAdapterListener {
            fun onItemClickListener( item: Comment)
        }

    }
}