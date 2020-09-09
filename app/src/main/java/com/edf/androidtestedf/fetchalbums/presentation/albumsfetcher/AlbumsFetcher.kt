package com.edf.androidtestedf.fetchalbums.presentation.albumsfetcher

import com.edf.androidtestedf.fetchalbums.domain.repository.interfaces.IAlbumsFetcherRepository
import com.edf.androidtestedf.fetchalbums.presentation.albumsfetcher.interfaces.IAlbumFetcher
import com.edf.androidtestedf.fetchalbums.presentation.viewmodel.interfaces.IPresentAlbums
import com.edf.androidtestedf.fetchalbums.presentation.viewmodel.interfaces.IPresentAlbumsError

class AlbumsFetcher(private val viewModelSuccess: IPresentAlbums,
                    private val viewModelError: IPresentAlbumsError,
                    private val albumsRepository: IAlbumsFetcherRepository
): IAlbumFetcher {
    override fun fetch() {
        albumsRepository.fetch({ albums ->
            viewModelSuccess.displayAlbums(albums)
        },{
            viewModelError.displayError()
        })
    }
}