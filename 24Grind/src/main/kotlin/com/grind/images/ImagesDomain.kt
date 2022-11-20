package com.grind.images

import com.fasterxml.jackson.annotation.JsonProperty

data class CloudinaryResponse(
    @JsonProperty("eager")
   val eager: List<CloudinaryEager>
)

data class CloudinaryEager(
    @JsonProperty("transformation")
    val transformation: String,
    @JsonProperty("url")
    val url: String,
    @JsonProperty("secure_url")
    val secureUrl: String
)