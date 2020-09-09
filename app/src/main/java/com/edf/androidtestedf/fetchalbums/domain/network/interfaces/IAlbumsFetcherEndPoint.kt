package com.edf.androidtestedf.fetchalbums.domain.network.interfaces

import com.edf.androidtestedf.fetchalbums.domain.network.models.AlbumFromWs
import retrofit2.http.GET

interface IAlbumsFetcherEndPoint {
    @GET("albums")
    suspend fun fetchAlbums(): List<AlbumFromWs>
}