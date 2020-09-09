package com.edf.androidtestedf.fetchphotos.domain.repository.interfaces

import com.edf.androidtestedf.fetchphotos.domain.db.models.PhotoFromDb

interface IPhotosFetcherRepository {
    fun fetch(success: (List<PhotoFromDb>) -> Unit, error: () -> Unit)
}