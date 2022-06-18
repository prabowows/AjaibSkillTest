package com.skilltest.viewmodel

import android.content.Intent
import androidx.lifecycle.*
import com.skilltest.activity.DetailUserActivity
import com.skilltest.model.response.UserDetailResponse
import com.skilltest.model.response.UserRepoResponse
import com.skilltest.repository.EmployeeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailUserViewModel(
    private val dispatcher: CoroutineDispatcher,
    private val employeeRepository: EmployeeRepository
) : ViewModel(), LifecycleObserver {
    var loading: MutableLiveData<Boolean> = MutableLiveData()
    var userDetailLiveData: MutableLiveData<UserDetailResponse> = MutableLiveData()
    var userRepoLiveData: MutableLiveData<UserRepoResponse> = MutableLiveData()
    var userId = ""

    init {
        loading.postValue(true)
    }

    fun processIntent(intent: Intent) {
        if (intent.hasExtra(DetailUserActivity.EXTRA_USER_ID)) {
            userId = intent.getStringExtra(DetailUserActivity.EXTRA_USER_ID).orEmpty()
        }
    }

    fun fetchDetailUser(user : String) {

        viewModelScope.launch(dispatcher) {
            loading.postValue(true)
            try {
                employeeRepository.fetchDetailUser(username = user).collect { response ->
                    if (response.isSuccessful) {
                        loading.postValue(false)
                        val employeeInfo = response.body()
                        employeeInfo?.let { model ->
                            userDetailLiveData.value = model
                        }
                    } else {
                        loading.postValue(false)
                    }
                }
            } catch (e: Exception) {
                loading.postValue(false)
                e.printStackTrace()
            }
        }
    }

    fun fetchRepoUser(user : String) {

        viewModelScope.launch(dispatcher) {
            loading.postValue(true)
            try {
                employeeRepository.fetchRepoUser(username = user).collect { response ->
                    if (response.isSuccessful) {
                        loading.postValue(false)
                            response.body()?.let { model ->
                            userRepoLiveData.value = model
                        }
                    } else {
                        loading.postValue(false)
                    }
                }
            } catch (e: Exception) {
                loading.postValue(false)
                e.printStackTrace()
            }
        }
    }

    /**
     * Fetch load status
     *
     * @return
     */
    fun fetchLoadStatus(): LiveData<Boolean> = loading
}