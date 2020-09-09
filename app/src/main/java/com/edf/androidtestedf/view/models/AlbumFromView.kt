package com.edf.androidtestedf.view.models

import com.edf.androidtestedf.fetchphotos.domain.db.models.PhotoFromDb

data class AlbumFromView(
    var id: Int = 0,
    var title : String? = "",
    var photos: List<PhotoFromDb>)