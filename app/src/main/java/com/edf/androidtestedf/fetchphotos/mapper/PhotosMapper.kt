package com.edf.androidtestedf.fetchphotos.mapper

import com.edf.androidtestedf.fetchphotos.domain.db.models.PhotoFromDb
import com.edf.androidtestedf.fetchphotos.domain.network.models.PhotoFromWs

fun PhotoFromWs.toPhotoFromDb() =
    PhotoFromDb(
        albumId = albumId,
        id = id,
        thumbnailUrl = thumbnailUrl
    )
