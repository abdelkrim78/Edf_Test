package com.edf.androidtestedf.view

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.edf.androidtestedf.R
import com.edf.androidtestedf.fetchalbums.factory.AlbumsLoadedFactory
import com.edf.androidtestedf.fetchalbums.presentation.viewmodel.AlbumsFetcherViewModel
import com.edf.androidtestedf.fetchalbums.presentation.viewmodel.state.GetAlbumsState
import com.edf.androidtestedf.fetchalbums.presentation.viewmodel.state.GetAlbumsStateError
import com.edf.androidtestedf.fetchalbums.presentation.viewmodel.state.GetAlbumsStateSuccess
import com.edf.androidtestedf.view.models.AlbumFromView
import kotlinx.android.synthetic.main.fragment_albums.*

class AlbumsFragment : Fragment() {
    private val albumsFetcherViewModel by lazy {
        AlbumsFetcherViewModel()
    }
    private val albumsFetcherController by lazy {
        AlbumsLoadedFactory.make(albumsFetcherViewModel, requireContext())
    }
    private lateinit var albumsAdapter: AlbumsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        albumsFetcherController.didLoad()
     }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_albums, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoader()
        initAdapter()
        initViewModelsObserver()
        initSwipeRefresh()
    }

    //region adapter
    private fun initAdapter() {
        albumsAdapter = AlbumsAdapter(listOf())
        albums_rv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = albumsAdapter
        }
    }
    //endregion adapter

    //region swipeRefresh
    private fun initSwipeRefresh() {
        albums_refresh.apply {
            setProgressBackgroundColorSchemeColor(Color.WHITE)
            setColorSchemeColors(ContextCompat.getColor(context, R.color.colorAccent))
            setOnRefreshListener {
                hideWorkspacesRV()
                albumsFetcherController.didLoad()
            }
        }
    }
    //endregion swipeRefresh

    //region viewModels
    private fun initViewModelsObserver() {
        initFetchAlbumsObserver()
    }

    private fun initFetchAlbumsObserver() {
        albumsFetcherViewModel.getAlbums.observe(viewLifecycleOwner, Observer { state ->
            manageFetchAlbumsResult(state)
        })
    }
    //endregion viewModels

    //region albums
    private fun manageFetchAlbumsResult(state: GetAlbumsState?) {
        when(state) {
            is GetAlbumsStateSuccess -> displayAlbums(state.albums)
            is GetAlbumsStateError -> displayAlbumsError()
        }
    }

    private fun displayAlbums(albums: List<AlbumFromView>) {
        albums_refresh.isRefreshing = false
        hideLoader()
        albumsAdapter.notifyListChange(albums)
        showWorkspacesRV()
    }


    private fun displayAlbumsError() {
        albums_refresh.isRefreshing = false
        hideLoader()
        //todo manage error screen
    }
    //region albums

    //region loader
    private fun showLoader() {
        albums_loader.visibility = View.VISIBLE
    }

    private fun hideLoader() {
        albums_loader.visibility = View.GONE
    }
    //endregion loader

    //region recycler visibility
    private fun showWorkspacesRV() {
        albums_rv.visibility = View.VISIBLE
    }

    private fun hideWorkspacesRV() {
        albums_rv.visibility = View.GONE
    }
    //endregion recycler visibility
}