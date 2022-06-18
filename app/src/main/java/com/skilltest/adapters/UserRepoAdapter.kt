package com.skilltest.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skilltest.R
import com.skilltest.model.UserRepoModel
import kotlinx.android.synthetic.main.item_user_list.view.*
import kotlinx.android.synthetic.main.item_user_repo.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import androidx.core.content.ContextCompat.startActivity

import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat


class UserRepoAdapter(
    var users: ArrayList<UserRepoModel>,
    var context: Context,
    var onClick: (String) -> Unit
) : RecyclerView.Adapter<UserRepoAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_user_repo, parent, false)
        )

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) =
        holder.bind(users[position])

    fun refreshAdapter(newUsers: List<UserRepoModel>) {
        users.clear()
        users.addAll(newUsers)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(UserRepoModel: UserRepoModel) {
            itemView.nameRepoTextView.text = UserRepoModel.name
            itemView.descRepoTextView.text = UserRepoModel.description ?: "No Description"
            itemView.starRepoTextView.text = UserRepoModel.stargazersCount.toString()
            itemView.dateRepoTextView.text = convertDate(UserRepoModel.updatedAt ?: "")
            Glide.with(context).load(UserRepoModel.owner?.avatarUrl).circleCrop().placeholder(R.drawable.ic_sync).into(itemView.avatarImageView)
            itemView.setOnClickListener {
                onClick(UserRepoModel.htmlUrl.orEmpty())
            }
        }
    }

    private fun convertDate(date: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val outputFormat = SimpleDateFormat("dd MMMM yyyy")
        val parsedDate: Date = inputFormat.parse(date)
        val formattedDate: String = outputFormat.format(parsedDate)
        return formattedDate
    }

}