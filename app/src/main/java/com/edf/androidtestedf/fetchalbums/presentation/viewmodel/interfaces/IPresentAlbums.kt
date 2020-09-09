package com.edf.androidtestedf.fetchalbums.presentation.viewmodel.interfaces

import com.edf.androidtestedf.fetchalbums.domain.db.models.AlbumFromDb

interface IPresentAlbums {
    fun displayAlbums(albums: List<AlbumFromDb>)
}