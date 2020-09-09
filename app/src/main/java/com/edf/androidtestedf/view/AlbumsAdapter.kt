package com.edf.androidtestedf.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edf.androidtestedf.R
import com.edf.androidtestedf.app.utils.PictureUtils.loadPicture
import com.edf.androidtestedf.view.models.AlbumFromView
import kotlinx.android.synthetic.main.albums_rv_item.view.*

class AlbumsAdapter(private var albums: List<AlbumFromView>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.albums_rv_item, parent, false)
        return AlbumsViewHolder(viewItem)
    }

    override fun getItemCount(): Int = albums.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? AlbumsViewHolder)?.bindView(albums[position])
    }

    fun notifyListChange(albums: List<AlbumFromView>) {
        this.albums = albums
        this.notifyDataSetChanged()
    }

    class AlbumsViewHolder(private var view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(album: AlbumFromView) {
            view.apply {
                album_title_txt.text = album.title
                loadPicture(context, album.photos[0].thumbnailUrl, album_picture_one)
                loadPicture(context, album.photos[1].thumbnailUrl, album_picture_two)
                loadPicture(context, album.photos[2].thumbnailUrl, album_picture_three)
            }
        }
    }
}