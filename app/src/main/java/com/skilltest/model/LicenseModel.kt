package com.skilltest.model


import com.google.gson.annotations.SerializedName

data class LicenseModel(
    @SerializedName("key")
    var key: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("node_id")
    var nodeId: String? = null,
    @SerializedName("spdx_id")
    var spdxId: String? = null,
    @SerializedName("url")
    var url: String? = null
)