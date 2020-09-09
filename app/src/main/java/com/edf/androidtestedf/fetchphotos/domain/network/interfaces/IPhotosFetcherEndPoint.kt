package com.edf.androidtestedf.fetchphotos.domain.network.interfaces

import com.edf.androidtestedf.fetchphotos.domain.network.models.PhotoFromWs
import retrofit2.http.GET

interface IPhotosFetcherEndPoint {
    @GET("photos")
    suspend fun fetchPhotos(): List<PhotoFromWs>
}