package com.skilltest.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skilltest.R
import com.skilltest.model.UserModel
import kotlinx.android.synthetic.main.item_user_list.view.*

class UserAdapter(var users: ArrayList<UserModel>, var context: Context, var onClick: (String) -> Unit) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder  =
        UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user_list,parent,false))

    override fun getItemCount(): Int  = users.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int)  = holder.bind(users[position])

    fun refreshAdapter( newUsers:List<UserModel>){
        users.clear()
        users.addAll(newUsers)
        notifyDataSetChanged()
    }

    fun clearAdapter(){
        users.clear()
        notifyDataSetChanged()
    }

    inner  class UserViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private  val layout = view.item_layout
        private  val firstName = view.first_name
        private val lastName = view.last_name
        private val email = view.email
        private val image = view.imageView
        private val accoutId = view.accountIdTextView
        fun bind(userModel: UserModel){
            firstName.text = userModel.login
            lastName.text = userModel.htmlUrl
            email.text = userModel.reposUrl
            accoutId.text = userModel.id.toString()
            Glide.with(context).load(userModel.avatarUrl).circleCrop().placeholder(R.drawable.ic_sync).into(image)
            layout.setOnClickListener { 
                onClick(userModel.login.orEmpty())
            }
        }
    }
}