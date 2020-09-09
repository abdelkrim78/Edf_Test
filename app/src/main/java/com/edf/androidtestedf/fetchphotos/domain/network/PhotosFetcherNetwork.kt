package com.edf.androidtestedf.fetchphotos.domain.network

import com.edf.androidtestedf.fetchphotos.domain.network.interfaces.IPhotosFetcherEndPoint
import com.edf.androidtestedf.fetchphotos.domain.network.interfaces.IPhotosFetcherNetwork
import com.edf.androidtestedf.fetchphotos.domain.network.models.PhotoFromWs
import kotlinx.coroutines.*

class PhotosFetcherNetwork(private val api : IPhotosFetcherEndPoint): IPhotosFetcherNetwork {

    private lateinit var job: Job
    private lateinit var exceptionHandler: CoroutineExceptionHandler

    override fun fetch(success: (List<PhotoFromWs>) -> Unit, error: () -> Unit) {
        initExceptionHandler(error)
        initJob(success)
        cancelJob()
    }

    private fun initExceptionHandler(error: () -> Unit) {
        exceptionHandler = CoroutineExceptionHandler { _, _ ->
            CoroutineScope(Dispatchers.Main).launch { error.invoke() }
        }
    }

    private fun initJob(success: (List<PhotoFromWs>) -> Unit) {
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

    private suspend fun callApi() = api.fetchPhotos()
}