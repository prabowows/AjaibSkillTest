package com.skilltest.viewmodel

import androidx.lifecycle.*
import com.skilltest.model.response.GithubUserResponse
import com.skilltest.repository.EmployeeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ListUserViewModel(
    private val dispatcher: CoroutineDispatcher,
    private val employeeRepository: EmployeeRepository
) : ViewModel(), LifecycleObserver {
    var loading: MutableLiveData<Boolean> = MutableLiveData()
    var employeeLiveData: MutableLiveData<GithubUserResponse> = MutableLiveData()

    init {
        loading.postValue(true)
    }

    fun fetchEmployeeList(user: String) {

        viewModelScope.launch(dispatcher) {
            loading.postValue(true)
            try {
                employeeRepository.fetchListUser(username = user).collect { response ->
                    if (response.isSuccessful) {
                        loading.postValue(false)
                        val employeeInfo = response.body()
                        employeeInfo?.let { model ->
                            employeeLiveData.value = model
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