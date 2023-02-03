package com.prbansal.favtrack.api

import com.prbansal.favtrack.database.RepoData
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class RepoApiService {

    private lateinit var api: RepoApi
    val BASE_URL = "  https://api.github.com/repos/"

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        api = retrofit.create(RepoApi::class.java)
    }

    suspend fun getRepo(owner: String, repoName: String):RepoResponse = api.getMeals(owner,repoName)

    interface RepoApi {
        @GET("{owner}/{repoName}")
        suspend fun getMeals(@Path("owner") owner: String, @Path("repoName") repoName:String): RepoResponse
    }
}