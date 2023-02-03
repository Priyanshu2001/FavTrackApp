package com.prbansal.favtrack.api

data class RepoResponse (
    val full_name : String,
    val html_url : String,
    var description: String?,
        )