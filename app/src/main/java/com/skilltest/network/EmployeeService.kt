package com.skilltest.network

import com.skilltest.model.response.GithubUserResponse
import com.skilltest.model.response.UserDetailResponse
import com.skilltest.model.response.UserRepoResponse

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EmployeeService {

    @GET("/search/users")
    suspend fun fetchListUser(@Query("q") user: String): Response<GithubUserResponse>

    @GET("/users/{user_id}")
    suspend fun fetchDetailUser(@Path(value = "user_id", encoded = true) userId: String): Response<UserDetailResponse>

    @GET("/users/{user_id}/repos")
    suspend fun fetchRepoUser(@Path(value = "user_id", encoded = true) userId: String): Response<UserRepoResponse>
}