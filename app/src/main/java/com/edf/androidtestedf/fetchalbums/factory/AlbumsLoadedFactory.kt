package com.edf.androidtestedf.fetchalbums.factory

import android.content.Context
import com.edf.androidtestedf.app.network.RetrofitProvider.provideRetrofit
import com.edf.androidtestedf.fetchalbums.domain.db.AlbumDb
import com.edf.androidtestedf.fetchalbums.domain.network.AlbumsFetcherNetwork
import com.edf.androidtestedf.fetchalbums.domain.network.interfaces.IAlbumsFetcherEndPoint
import com.edf.androidtestedf.fetchalbums.domain.repository.AlbumsFetcherRepository
import com.edf.androidtestedf.fetchalbums.presentation.albumsfetcher.AlbumsFetcher
import com.edf.androidtestedf.fetchalbums.presentation.controller.AlbumsLoadedController
import com.edf.androidtestedf.fetchalbums.presentation.viewmodel.AlbumsFetcherViewModel
import com.edf.androidtestedf.fetchphotos.domain.network.PhotosFetcherNetwork
import com.edf.androidtestedf.fetchphotos.domain.network.interfaces.IPhotosFetcherEndPoint
import com.edf.androidtestedf.fetchphotos.domain.repository.PhotosFetcherRepository

class AlbumsLoadedFactory {
    companion object {
        fun make(viewModel: AlbumsFetcherViewModel,
                 context: Context): AlbumsLoadedController {
            val retrofit = provideRetrofit()
            val albumsEndPoint = retrofit.create(IAlbumsFetcherEndPoint::class.java)
            val photosEndPoint = retrofit.create(IPhotosFetcherEndPoint::class.java)
            val albumsNetwork = AlbumsFetcherNetwork(albumsEndPoint)
            val photosNetwork = PhotosFetcherNetwork(photosEndPoint)
            val photosRepository = PhotosFetcherRepository(photosNetwork)
            //AlbumDb.init(context)
            val db = AlbumDb()
            val repository = AlbumsFetcherRepository(albumsNetwork, photosRepository, db)
            val fetcher = AlbumsFetcher(viewModel, viewModel, repository)
            return AlbumsLoadedController(fetcher)
        }
    }
}