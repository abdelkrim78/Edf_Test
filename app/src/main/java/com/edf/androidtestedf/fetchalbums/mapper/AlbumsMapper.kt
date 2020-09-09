package com.edf.androidtestedf.fetchalbums.mapper

import com.edf.androidtestedf.fetchalbums.domain.db.models.AlbumFromDb
import com.edf.androidtestedf.fetchalbums.domain.network.models.AlbumFromWs
import com.edf.androidtestedf.view.models.AlbumFromView

fun AlbumFromWs.toAlbumFromDb() =
    AlbumFromDb(
        id = id,
        title = title,
        photos = listOf())


fun AlbumFromDb.toAlbumFromView() =
    AlbumFromView(
        id = id,
        title = title,
        photos = photos)