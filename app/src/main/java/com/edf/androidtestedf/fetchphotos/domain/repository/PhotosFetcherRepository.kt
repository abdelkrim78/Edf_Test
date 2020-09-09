package com.edf.androidtestedf.fetchphotos.domain.repository

import com.edf.androidtestedf.fetchphotos.domain.db.models.PhotoFromDb
import com.edf.androidtestedf.fetchphotos.domain.network.interfaces.IPhotosFetcherNetwork
import com.edf.androidtestedf.fetchphotos.domain.network.models.PhotoFromWs
import com.edf.androidtestedf.fetchphotos.domain.repository.interfaces.IPhotosFetcherRepository
import com.edf.androidtestedf.fetchphotos.mapper.toPhotoFromDb

class PhotosFetcherRepository(private val photosNetwork: IPhotosFetcherNetwork) :
    IPhotosFetcherRepository {
    override fun fetch(success: (List<PhotoFromDb>) -> Unit, error: () -> Unit) {
        photosNetwork.fetch({photos ->
            val photoDb = adaptResultToPhotosDb(photos)
            success.invoke(photoDb)
        }, {
            error.invoke()
        })
    }

    private fun adaptResultToPhotosDb(photos: List<PhotoFromWs>) =
        photos.map { it.toPhotoFromDb() }
}