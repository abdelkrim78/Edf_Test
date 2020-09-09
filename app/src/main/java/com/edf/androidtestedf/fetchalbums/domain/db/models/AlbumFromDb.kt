package com.edf.androidtestedf.fetchalbums.domain.db.models

import com.edf.androidtestedf.fetchphotos.domain.db.models.PhotoFromDb

data class AlbumFromDb(
    var id: Int = 0,
    var title: String? = "",
    var photos: List<PhotoFromDb> = listOf()
    )