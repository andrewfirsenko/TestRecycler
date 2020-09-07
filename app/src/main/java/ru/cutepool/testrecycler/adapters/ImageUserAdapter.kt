package ru.cutepool.testrecycler.adapters

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.item_image_user.view.*
import ru.cutepool.testrecycler.R


class ImageUserAdapter @JvmOverloads constructor(
    private var glide: RequestManager,
    private val links: MutableList<String> = mutableListOf()
) : RecyclerView.Adapter<ImageUserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("DEBUG", "imageUser createViewHolder")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("DEBUG", "imageUser bindViewHolder")
        with(holder.itemView) {
            glide.load("https://funik.ru/wp-content/uploads/2018/12/c92e40a3cfefffa1bbf2-700x1050.jpg")
                .addListener(object : RequestListener<Drawable> {
                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                        return false
                    }

                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {

                        return false
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(item_image_user__image)
        }
    }

    override fun getItemCount() = links.size

    override fun getItemId(position: Int): Long {
        return links[position].hashCode().toLong()
    }

    fun bindData(links: List<String>) {
        this.links.clear()
        this.links.addAll(links)
        notifyDataSetChanged()
    }

    fun setRequestManager(glide: RequestManager) {
        this.glide = glide
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
