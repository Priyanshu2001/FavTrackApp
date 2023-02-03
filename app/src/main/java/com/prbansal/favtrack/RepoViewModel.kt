package com.prbansal.favtrack

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.prbansal.favtrack.api.RepoApiService
import com.prbansal.favtrack.api.RepoResponse
import com.prbansal.favtrack.database.RepoData
import com.prbansal.favtrack.database.RepoDatabase
import com.prbansal.favtrack.database.RepoDatabaseDao
import kotlinx.coroutines.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response


class RepoViewModel(
    val application: Application,
) : ViewModel() {
    private val repoDao : RepoDatabaseDao
    val allRepos: LiveData<List<RepoData>>
    private val apiService = RepoApiService()
    var showCustomDialog = mutableStateOf(false)
    var showLoadingDialog = { mutableStateOf(false) }
    var error = { mutableStateOf(false) }
    init {
        val repoDb = RepoDatabase.getInstance(application)
        repoDao = repoDb.repoDatabaseDao

        allRepos = repoDao.getAllRepos()
    }


    var isSaved = false


    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


    suspend fun getRepo(owner:String, repoName: String) : RepoResponse{
        return  apiService.getRepo(owner, repoName)
    }




    private suspend fun insert(repo: RepoData) {
        withContext(Dispatchers.IO) {
            repoDao.insert(repo)
        }
    }

    private suspend fun delete(repo: RepoData) {
        withContext(Dispatchers.IO) {
            repoDao.deleteRepo(repo)
        }
    }


    fun onSaveRepoClicked(newRepo: RepoData){
        coroutineScope.launch {
            insert(newRepo)
        }
    }

    fun onDeleteRepoClicked(repo: RepoData){
        coroutineScope.launch {
            delete(repo)
        }
    }

    fun onAddRepoClicked(owner:String, repoName: String, onFailure:() -> Unit){
        coroutineScope.launch {
            try {
                val repo = getRepo(owner, repoName)
                val names = repo.full_name.split("/")
                if (repo.description == null)
                    repo.description = "This Repo has no Description"
                val repoData = RepoData(null, names[0], names[1], repo.description, repo.html_url)
                insert(repoData)
                Log.e("OYE HOYE HOYE", repo.html_url)
            }
            catch (e: Exception){
                onFailure()
                Log.e("OYE HOYE HOYE", "hh")
            }
//



        }
    }
}