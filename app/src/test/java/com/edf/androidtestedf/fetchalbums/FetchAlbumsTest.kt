package com.edf.androidtestedf.fetchalbums

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.edf.androidtestedf.fetchalbums.domain.db.interfaces.IAlbumDb
import com.edf.androidtestedf.fetchalbums.domain.network.interfaces.IAlbumsFetcherNetwork
import com.edf.androidtestedf.fetchalbums.domain.network.models.AlbumFromWs
import com.edf.androidtestedf.fetchalbums.domain.repository.AlbumsFetcherRepository
import com.edf.androidtestedf.fetchalbums.mapper.toAlbumFromDb
import com.edf.androidtestedf.fetchalbums.mapper.toAlbumFromView
import com.edf.androidtestedf.fetchalbums.presentation.albumsfetcher.AlbumsFetcher
import com.edf.androidtestedf.fetchalbums.presentation.controller.AlbumsLoadedController
import com.edf.androidtestedf.fetchalbums.presentation.viewmodel.AlbumsFetcherViewModel
import com.edf.androidtestedf.fetchalbums.presentation.viewmodel.state.GetAlbumsStateError
import com.edf.androidtestedf.fetchalbums.presentation.viewmodel.state.GetAlbumsStateSuccess
import com.edf.androidtestedf.fetchphotos.domain.network.interfaces.IPhotosFetcherNetwork
import com.edf.androidtestedf.fetchphotos.domain.network.models.PhotoFromWs
import com.edf.androidtestedf.fetchphotos.domain.repository.PhotosFetcherRepository
import com.edf.androidtestedf.fetchphotos.mapper.toPhotoFromDb
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class FetchAlbumsTest {
    /**
     * As a user of the application
     * I want to access to all albums and photos
     * In order to see the different albums name and photos
     */
    @get:Rule
    val rule = InstantTaskExecutorRule()

    //Scénario 0
    @Test
    fun `Given I am a user n When I ask to access the list albums Then i see all albums name and photos`() {
        val photosWs = arrayListOf<PhotoFromWs>()
        photosWs.apply {
            add(PhotoFromWs(1, 11))
            add(PhotoFromWs(1, 12))
            add(PhotoFromWs(1, 13))
            add(PhotoFromWs(2, 14))
            add(PhotoFromWs(2, 15))
            add(PhotoFromWs(2, 16))
            add(PhotoFromWs(3, 17))
            add(PhotoFromWs(3, 18))
            add(PhotoFromWs(3, 19))
        }
        val photosDb = photosWs.map { it.toPhotoFromDb() }
        val albumWs = arrayListOf<AlbumFromWs>()
        albumWs.apply {
            add(AlbumFromWs(1, "first album"))
            add(AlbumFromWs(2, "second album"))
            add(AlbumFromWs(3, "three album"))
        }
        val albumDb = albumWs.map { it.toAlbumFromDb() }
        photosDb.forEach {photo ->
            val album = albumDb.first { album -> photo.albumId ==  album.id}
            val photosFiltered = photosDb.filter { picture -> picture.albumId == album.id }
            album.photos = photosFiltered
        }
        val albumView = albumDb.map { it.toAlbumFromView() }
        val albumStateSuccess  = GetAlbumsStateSuccess(albumView)
        val viewModel = AlbumsFetcherViewModel()
        val albumsNetwork = mock<IAlbumsFetcherNetwork>()
        val photosNetwork = mock<IPhotosFetcherNetwork>()
        val db = mock<IAlbumDb>()
        val photosRepository = PhotosFetcherRepository(photosNetwork)
        val albumsRepository = AlbumsFetcherRepository(albumsNetwork, photosRepository, db)
        val albumsFetcher = AlbumsFetcher(viewModel, viewModel, albumsRepository)
        val controller = AlbumsLoadedController(albumsFetcher)
        val linkBehaviorCaptorAlbums = argumentCaptor<(List<AlbumFromWs>) -> Unit>()
        val linkBehaviorCaptorPhotos = argumentCaptor<(List<PhotoFromWs>) -> Unit>()
        val linkBehaviorCaptorError = argumentCaptor<() -> Unit>()
        Mockito.`when`(albumsNetwork.fetch(linkBehaviorCaptorAlbums.capture(), linkBehaviorCaptorError.capture())).thenAnswer { linkBehaviorCaptorAlbums.firstValue.invoke(albumWs) }
        Mockito.`when`(photosNetwork.fetch(linkBehaviorCaptorPhotos.capture(), linkBehaviorCaptorError.capture())).thenAnswer { linkBehaviorCaptorPhotos.firstValue.invoke(photosWs) }

        controller.didLoad()

        Assert.assertTrue(viewModel.getAlbums.value is GetAlbumsStateSuccess)
        Assert.assertEquals(albumStateSuccess.albums, (viewModel.getAlbums.value  as GetAlbumsStateSuccess).albums)
        Assert.assertTrue(albumStateSuccess.albums[0].photos.isNotEmpty())
    }

    //Scénario 1
    @Test
    fun `Given I am a user n When I ask to access the list of albums and there are not available Then i see the error screen`() {
        val viewModel = AlbumsFetcherViewModel()
        val albumsNetwork = mock<IAlbumsFetcherNetwork>()
        val photosNetwork = mock<IPhotosFetcherNetwork>()
        val db = mock<IAlbumDb>()
        val photosRepository = PhotosFetcherRepository(photosNetwork)
        val albumsRepository = AlbumsFetcherRepository(albumsNetwork, photosRepository, db)
        val albumsFetcher = AlbumsFetcher(viewModel, viewModel, albumsRepository)
        val controller = AlbumsLoadedController(albumsFetcher)
        val linkBehaviorCaptorAlbums = argumentCaptor<(List<AlbumFromWs>) -> Unit>()
        val linkBehaviorCaptorError = argumentCaptor<() -> Unit>()
        Mockito.`when`(albumsNetwork.fetch(linkBehaviorCaptorAlbums.capture(), linkBehaviorCaptorError.capture())).thenAnswer { linkBehaviorCaptorError.firstValue.invoke() }

        controller.didLoad()

        Assert.assertTrue(viewModel.getAlbums.value is GetAlbumsStateError)
    }
}