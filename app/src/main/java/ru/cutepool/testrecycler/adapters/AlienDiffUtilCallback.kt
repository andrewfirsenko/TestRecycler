package ru.cutepool.testrecycler.adapters

import androidx.recyclerview.widget.DiffUtil
import ru.cutepool.testrecycler.model.Alien

class AlienDiffUtilCallback(private val oldList: List<Alien>, private val newList: List<Alien>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val alien1 = oldList[oldItemPosition]
        val alien2 = newList[newItemPosition]
        return alien1.age == alien2.age
                && alien1.name == alien2.name
                && alien1.images == alien2.images
    }
}