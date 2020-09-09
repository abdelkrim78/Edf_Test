package com.edf.androidtestedf.fetchphotos.domain.network.models

data class PhotoFromWs(
    var albumId: Int = 0,
    var id: Int = 0,
    var thumbnailUrl: String = ""
)