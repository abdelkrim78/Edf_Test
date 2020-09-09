package com.edf.androidtestedf.fetchalbums.presentation.viewmodel.state

import com.edf.androidtestedf.view.models.AlbumFromView

sealed class GetAlbumsState
class GetAlbumsStateSuccess(val albums: List<AlbumFromView>) : GetAlbumsState()
object GetAlbumsStateError : GetAlbumsState()
