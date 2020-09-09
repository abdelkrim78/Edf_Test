package com.edf.androidtestedf.fetchalbums.domain.repository

import com.edf.androidtestedf.fetchalbums.domain.db.interfaces.IAlbumDb
import com.edf.androidtestedf.fetchalbums.domain.db.models.AlbumFromDb
import com.edf.androidtestedf.fetchalbums.domain.network.interfaces.IAlbumsFetcherNetwork
import com.edf.androidtestedf.fetchalbums.domain.network.models.AlbumFromWs
import com.edf.androidtestedf.fetchalbums.domain.repository.interfaces.IAlbumsFetcherRepository
import com.edf.androidtestedf.fetchalbums.mapper.toAlbumFromDb
import com.edf.androidtestedf.fetchphotos.domain.db.models.PhotoFromDb
import com.edf.androidtestedf.fetchphotos.domain.repository.interfaces.IPhotosFetcherRepository

class AlbumsFetcherRepository(private val albumsNetwork: IAlbumsFetcherNetwork,
                              private val photosRepository: IPhotosFetcherRepository,
                              private val albumDb: IAlbumDb
): IAlbumsFetcherRepository {

    override fun fetch(success: (List<AlbumFromDb>) -> Unit, error: () -> Unit) {

        // mode offline or cache is valide
        //val albumsFromDb = getAlbumsFromDb()
        //if (albumsFromDb.isNullOrEmpty()) getAlbumsFromNetwork(success, error) else success.invoke(albumsFromDb)

        getAlbumsFromNetwork(success, error)

    }

    private fun getAlbumsFromNetwork(success: (List<AlbumFromDb>) -> Unit, error: () -> Unit) {
        albumsNetwork.fetch({ albums ->
            val albumsFromDb = adaptResultToAlbumsDb(albums)
            fetchPhotos(albumsFromDb, success, error)
        }, {
            error.invoke()
        })
    }

    private fun fetchPhotos(albums: List<AlbumFromDb>, success: (List<AlbumFromDb>) -> Unit, error: () -> Unit) {
        photosRepository.fetch({ photos ->
            addPhotosInAlbums(albums, photos, success)
        }, {
            error.invoke()
        })
    }

    private fun addPhotosInAlbums(albums: List<AlbumFromDb>, photos: List<PhotoFromDb>, success: (List<AlbumFromDb>) -> Unit) {
        photos.forEach {photo ->
            val album = albums.first { album -> photo.albumId ==  album.id}
            val photosFiltered = photos.filter { picture -> picture.albumId == album.id }
            album.photos = photosFiltered
        }
        // for mode offline
        //albumDb.saveAlbums(albums)
        success.invoke(albums)
    }

    //region db
    private fun getAlbumsFromDb() = albumDb.getAlbums()

    private fun adaptResultToAlbumsDb(albums: List<AlbumFromWs>) =
        albums.map { it.toAlbumFromDb() }
    //endregion db
}