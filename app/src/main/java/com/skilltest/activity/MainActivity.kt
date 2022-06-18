package com.skilltest.activity

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.skilltest.R
import com.skilltest.adapters.UserAdapter
import com.skilltest.viewmodel.EmployeeViewModelFactory
import com.skilltest.viewmodel.ListUserViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var listUserViewModel: ListUserViewModel
    @VisibleForTesting
    var userAdapter: UserAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initAdapter()
        initViewModel()
        observeLoading()
        observeEmployeeInfo()
        fetchEmployeeList("a")

        searchCV.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                fetchEmployeeList(query.orEmpty())
                searchCV.clearFocus()
                hideKeyboard()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null && newText.length >= 3) {
                    fetchEmployeeList(newText)
                } else {
                    userAdapter?.clearAdapter()
                }
                return false
            }
        })
    }

    fun hideKeyboard() {
        val view: View? = this.currentFocus
        val inputMethodManager: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun initAdapter() {
        userAdapter = UserAdapter(arrayListOf(), this, onClick = { userId ->
            startActivity(DetailUserActivity.onNewIntent(this, userId))
        })
        userListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userAdapter
        }
    }

    private fun initViewModel() {
        var employeeViewModelFactory = EmployeeViewModelFactory()
        listUserViewModel = ViewModelProvider(
            this,
            employeeViewModelFactory
        ).get(ListUserViewModel::class.java)
    }

    private fun fetchEmployeeList(user: String) {
        listUserViewModel.fetchEmployeeList(user)
    }

    private fun observeEmployeeInfo() {
        listUserViewModel.employeeLiveData.observe(this, Observer { response ->
            response?.let { employeeModel ->
                employeeModel?.let {
                    response.items?.let { listData -> userAdapter?.refreshAdapter(listData) }
                }
            }
        })
    }

    private fun observeLoading() {
        listUserViewModel.fetchLoadStatus().observe(this, Observer {
            if (!it) {
                println(it)
                loading_progressBar.visibility = View.GONE
            } else {
                loading_progressBar.visibility = View.VISIBLE
            }
        })
    }
}