package com.edf.androidtestedf.fetchalbums.domain.db.interfaces

import com.edf.androidtestedf.fetchalbums.domain.db.models.AlbumFromDb

interface IAlbumDb {
    fun saveAlbums(albums: List<AlbumFromDb>)
    fun getAlbums() : List<AlbumFromDb>?
}