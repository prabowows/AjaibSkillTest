package com.skilltest.repository

import com.skilltest.model.response.GithubUserResponse
import com.skilltest.model.response.UserDetailResponse
import com.skilltest.model.response.UserRepoResponse
import com.skilltest.network.EmployeeService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class EmployeeRepository(private val apiServiceAPI: EmployeeService) {

    suspend fun fetchListUser(username: String): Flow<Response<GithubUserResponse>> {
        return flow {
            val employeeInfo = apiServiceAPI.fetchListUser(username)
            emit(employeeInfo)
        }
    }

    suspend fun fetchDetailUser(username: String): Flow<Response<UserDetailResponse>> {
        return flow {
            val employeeInfo = apiServiceAPI.fetchDetailUser(username)
            emit(employeeInfo)
        }
    }

    suspend fun fetchRepoUser(username: String): Flow<Response<UserRepoResponse>> {
        return flow {
            val employeeInfo = apiServiceAPI.fetchRepoUser(username)
            emit(employeeInfo)
        }
    }
}