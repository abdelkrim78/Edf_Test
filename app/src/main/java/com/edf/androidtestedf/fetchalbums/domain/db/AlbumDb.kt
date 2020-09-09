package com.edf.androidtestedf.fetchalbums.domain.db

import com.edf.androidtestedf.fetchalbums.domain.db.interfaces.IAlbumDb
import com.edf.androidtestedf.fetchalbums.domain.db.models.AlbumFromDb

class AlbumDb : IAlbumDb {
    // save data
    override fun saveAlbums(albums: List<AlbumFromDb>) {
        TODO("Not yet implemented")
    }

    override fun getAlbums(): List<AlbumFromDb>? {
        TODO("Not yet implemented")
    }
}
    /*companion object {
        private const val DB_NAME = "AlbumDb.realm"
        val realmDefaultInstance: Realm get() = Realm.getInstance(REALM_CONFIG)
        private var mInstance: IAlbumDb = AlbumDb()
        private lateinit var REALM_CONFIG: RealmConfiguration

        @Synchronized
        fun init(context: Context): IAlbumDb {
            Realm.init(context)
            REALM_CONFIG = RealmConfiguration.Builder().name(DB_NAME)
                .modules(AlbumRealmModule())
                .build()
            Realm.setDefaultConfiguration(REALM_CONFIG)
            return mInstance
        }

        @Synchronized
        fun getInstance(): IAlbumDb = mInstance

    }

    override fun saveAlbums(albums: List<AlbumFromDb>) {
        try {

            realmDefaultInstance.executeTransaction { realm -> realm.insertOrUpdate(albums) }
        } catch (exception: Exception) {
            Log.d(DB_NAME, "Save Albums Failed")
        } finally {
            realmDefaultInstance.close()
        }
    }

    override fun getAlbums(): List<AlbumFromDb>? {
        return try {
            var albums: List<AlbumFromDb>? =
                realmDefaultInstance.where(AlbumFromDb::class.java).findAll()
            albums?.let { listAlbum ->
                albums = realmDefaultInstance.copyFromRealm(listAlbum)
            }
            albums
        } catch (exception: Exception) {
            Log.d(DB_NAME, "Get Albums Failed")
            null
        } finally {
            realmDefaultInstance.close()
        }
    }
}

@RealmModule(classes = [AlbumFromDb::class])
class AlbumRealmModule*/