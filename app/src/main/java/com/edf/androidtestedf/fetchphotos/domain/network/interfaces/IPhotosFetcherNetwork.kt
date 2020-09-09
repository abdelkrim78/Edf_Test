package com.edf.androidtestedf.fetchphotos.domain.network.interfaces

import com.edf.androidtestedf.fetchphotos.domain.network.models.PhotoFromWs

interface IPhotosFetcherNetwork {
    fun fetch(success : (List<PhotoFromWs>) -> Unit, error: () -> Unit)
}