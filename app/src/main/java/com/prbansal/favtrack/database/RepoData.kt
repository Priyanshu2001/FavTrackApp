package com.prbansal.favtrack.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "repo_data_table")
data class RepoData(

    @PrimaryKey(autoGenerate = true)
    val repoId: Int?,

    @ColumnInfo(name = "owner")
    var ownerName: String,

    @ColumnInfo(name = "repo")
    var repoName: String,

    @ColumnInfo(name = "description")
    var description: String?,

    @ColumnInfo(name = "link")
    var link: String,
)