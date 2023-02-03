package com.prbansal.favtrack.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RepoDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun  insert(repoData: RepoData)

    @Query("SELECT * FROM repo_data_table ORDER BY repoId DESC")
    fun getAllRepos(): LiveData<List<RepoData>>

    @Query("SELECT * from repo_data_table WHERE repoId = :key")
    fun get(key: Int): LiveData<RepoData>

    @Delete
    fun deleteRepo(repoData: RepoData)
}