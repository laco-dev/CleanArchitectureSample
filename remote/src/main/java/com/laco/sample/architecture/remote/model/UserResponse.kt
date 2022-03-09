package com.laco.sample.architecture.remote.model

import com.google.gson.annotations.SerializedName

internal data class UserResponse(
    @SerializedName("results")
    val results: List<Result>
) {

    data class Result(
        @SerializedName("name")
        val name: Name,
        @SerializedName("picture")
        val picture: Picture
    ) {

        data class Name(
            @SerializedName("first")
            val first: String,
            @SerializedName("last")
            val last: String,
        )

        data class Picture(
            @SerializedName("thumbnail")
            val imageUrl: String
        )
    }
}
