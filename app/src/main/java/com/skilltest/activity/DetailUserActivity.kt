package com.skilltest.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.skilltest.R
import com.skilltest.adapters.UserRepoAdapter
import com.skilltest.viewmodel.DetailUserViewModel
import com.skilltest.viewmodel.DetailUserViewModelFactory
import kotlinx.android.synthetic.main.activity_detail_user.*
import kotlinx.android.synthetic.main.activity_detail_user.loading_progressBar

class DetailUserActivity : AppCompatActivity() {
    @VisibleForTesting
    lateinit var detailUserViewModel: DetailUserViewModel
    @VisibleForTesting
    var userRepoAdapter: UserRepoAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)
        initViewModel()
        observeLoading()
        observeEmployeeInfo()
        initAdapter()
        detailUserViewModel.processIntent(intent)
        fetchData(userId = detailUserViewModel.userId)
    }

    private fun initViewModel() {
        var employeeViewModelFactory = DetailUserViewModelFactory()
        detailUserViewModel = ViewModelProvider(this, employeeViewModelFactory).get(
            DetailUserViewModel::class.java
        )
    }

    private fun fetchData(userId: String?) {
        detailUserViewModel.fetchDetailUser(user = userId.orEmpty())
        detailUserViewModel.fetchRepoUser(user = userId.orEmpty())
    }

    private fun openBrowser(url : String){
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    private fun initAdapter() {
        userRepoAdapter = UserRepoAdapter(arrayListOf(), this, onClick = { url ->
            openBrowser(url)
        })
        userRepoRV.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userRepoAdapter
        }
    }

    private fun observeEmployeeInfo() {
        detailUserViewModel.userDetailLiveData.observe(this, Observer { response ->
            nameAccountTextView.text = response.name ?: "-"
            accountNameTextView.text = response.login ?: "-"
            accountAddressTextView.text = response.company ?: "No Information"
            followerAccountTextview.text = if ((response.followers ?: 0) > 0) {
                response.followers.toString()
            } else {
                "-"
            }
            placeAccountTextview.text = response.location ?: "No Information"
            emailAccountTextview.text = response.email ?: "No Information"
            Glide.with(this).load(response.avatarUrl).circleCrop().placeholder(R.drawable.ic_sync).into(avatarImageView)
        })

        detailUserViewModel.userRepoLiveData.observe(this, Observer { response ->
            userRepoAdapter?.refreshAdapter(response)
            profileView.visibility = View.VISIBLE
            repoAccountTextview.text = response.size.toString()
        })
    }

    private fun observeLoading() {
        detailUserViewModel.fetchLoadStatus().observe(this, Observer {
            if (!it) {
                println(it)
                loading_progressBar.visibility = View.GONE
            } else {
                loading_progressBar.visibility = View.VISIBLE
            }

        })
    }

    companion object {
        const val EXTRA_USER_ID = "extra_user_id"

        fun onNewIntent(activity: Context, userId: String? = ""): Intent {
            val intent = Intent(activity, DetailUserActivity::class.java)
            intent.putExtra(EXTRA_USER_ID, userId)
            return intent
        }
    }
}