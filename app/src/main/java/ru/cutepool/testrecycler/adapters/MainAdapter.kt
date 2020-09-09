package ru.cutepool.testrecycler.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_user.*
import ru.cutepool.testrecycler.R
import ru.cutepool.testrecycler.model.Alien
import ru.cutepool.testrecycler.view.StoriesView

class MainAdapter @JvmOverloads constructor(
    private val pauseListener: StoriesView.PauseListener,
    private val users: MutableList<Alien> = mutableListOf()
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    private lateinit var context: Context
    private val recycledViewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("DEBUG", "main createViewHolder")
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("DEBUG", "main bindViewHolder $position")
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size

    fun getUser(position: Int): Alien = users[position]

    fun update(users: List<Alien>) {
        if (users.isEmpty()) {
            return
        }

//        for (u in users) {
//            Log.d("DEBUG", "user ${u.id} links:")
//            for (i in u.images) {
//                Log.d("DEBUG", "\t${i}")
//            }
//        }

        val prevUsers: List<Alien> = ArrayList(this.users)
        val setNewUsersId = users.map { m -> m.id }.toHashSet()
        this.users.removeAll { m -> setNewUsersId.contains(m.id) }

        this.users.addAll(users)

        DiffUtil.calculateDiff(AlienDiffUtilCallback(prevUsers, this.users))
            .dispatchUpdatesTo(this)
    }

    override fun getItemId(position: Int): Long = users[position].id.hashCode().toLong()


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer {

        override val containerView: View?
            get() = itemView

        fun bind(user: Alien) {
            item_user__images.bindUser(user)
        }

        init {
            item_user__images.setRecycledViewPool(recycledViewPool)
            item_user__images.setOnPauseListener(pauseListener)
        }
    }
}