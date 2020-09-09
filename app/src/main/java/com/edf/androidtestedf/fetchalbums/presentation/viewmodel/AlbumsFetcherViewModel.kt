package com.edf.androidtestedf.fetchalbums.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.edf.androidtestedf.fetchalbums.domain.db.models.AlbumFromDb
import com.edf.androidtestedf.fetchalbums.mapper.toAlbumFromView
import com.edf.androidtestedf.fetchalbums.presentation.viewmodel.interfaces.IPresentAlbums
import com.edf.androidtestedf.fetchalbums.presentation.viewmodel.interfaces.IPresentAlbumsError
import com.edf.androidtestedf.fetchalbums.presentation.viewmodel.state.GetAlbumsState
import com.edf.androidtestedf.fetchalbums.presentation.viewmodel.state.GetAlbumsStateError
import com.edf.androidtestedf.fetchalbums.presentation.viewmodel.state.GetAlbumsStateSuccess

class AlbumsFetcherViewModel: ViewModel(), IPresentAlbums, IPresentAlbumsError {

    private val albums = MutableLiveData<GetAlbumsState>()
    val getAlbums: LiveData<GetAlbumsState>
        get() = albums

    override fun displayAlbums(albums: List<AlbumFromDb>) {
        val albumsFromView = adaptDbDataToView(albums)
        this.albums.postValue(GetAlbumsStateSuccess(albumsFromView))
    }

    private fun adaptDbDataToView(albumsDb: List<AlbumFromDb>) =
        albumsDb.map { it.toAlbumFromView() }

    override fun displayError() {
        this.albums.postValue(GetAlbumsStateError)
    }
}