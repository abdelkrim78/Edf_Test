package com.edf.androidtestedf.fetchalbums.domain.network.interfaces

import com.edf.androidtestedf.fetchalbums.domain.network.models.AlbumFromWs

interface IAlbumsFetcherNetwork{
    fun fetch(success : (List<AlbumFromWs>) -> Unit, error: () -> Unit)
}