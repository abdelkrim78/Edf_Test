package com.edf.androidtestedf.fetchalbums.presentation.controller

import com.edf.androidtestedf.fetchalbums.presentation.albumsfetcher.interfaces.IAlbumFetcher
import com.edf.androidtestedf.fetchalbums.presentation.controller.interfaces.IAlbumsLoadedController

class AlbumsLoadedController(private val fetcher: IAlbumFetcher) : IAlbumsLoadedController {
    override fun didLoad() = fetcher.fetch()
}