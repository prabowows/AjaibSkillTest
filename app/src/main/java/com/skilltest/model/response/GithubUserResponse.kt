package com.skilltest.model.response

import com.google.gson.annotations.SerializedName
import com.skilltest.model.UserModel

data class GithubUserResponse(
    @SerializedName("incomplete_results")
    var incompleteResults: Boolean? = null,
    @SerializedName("items")
    var items: List<UserModel>? = null,
    @SerializedName("total_count")
    var totalCount: Int? = null
)