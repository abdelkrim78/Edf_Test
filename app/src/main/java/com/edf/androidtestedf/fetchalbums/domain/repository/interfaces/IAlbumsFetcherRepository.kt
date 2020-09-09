package com.edf.androidtestedf.fetchalbums.domain.repository.interfaces

import com.edf.androidtestedf.fetchalbums.domain.db.models.AlbumFromDb

interface IAlbumsFetcherRepository {
    fun fetch(success: (List<AlbumFromDb>) -> Unit, error: () -> Unit)
}