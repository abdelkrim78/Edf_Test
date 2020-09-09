package com.edf.androidtestedf.fetchalbums.domain.network

import com.edf.androidtestedf.fetchalbums.domain.network.interfaces.IAlbumsFetcherEndPoint
import com.edf.androidtestedf.fetchalbums.domain.network.interfaces.IAlbumsFetcherNetwork
import com.edf.androidtestedf.fetchalbums.domain.network.models.AlbumFromWs
import kotlinx.coroutines.*

class AlbumsFetcherNetwork(private val api: IAlbumsFetcherEndPoint): IAlbumsFetcherNetwork {

    private lateinit var job: Job
    private lateinit var exceptionHandler: CoroutineExceptionHandler

    override fun fetch(success: (List<AlbumFromWs>) -> Unit, error: () -> Unit) {
        initExceptionHandler(error)
        initJob(success)
        cancelJob()
    }

    private fun initExceptionHandler(error: () -> Unit) {
        exceptionHandler = CoroutineExceptionHandler { _, _ ->
            CoroutineScope(Dispatchers.Main).launch { error.invoke() }
        }
    }

    private fun initJob(success: (List<AlbumFromWs>) -> Unit) {
        job = CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {
            val result = callApi()
            withContext(Dispatchers.Main) { success(result)  }
        }
    }

    private fun cancelJob() {
        job.invokeOnCompletion {
            job.cancel()
            exceptionHandler.cancel()
        }
    }

    private suspend fun callApi() = api.fetchAlbums()
}