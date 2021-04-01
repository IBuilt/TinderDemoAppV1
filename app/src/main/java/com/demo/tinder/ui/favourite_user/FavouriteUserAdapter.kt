package com.demo.tinder.ui.favourite_user

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.demo.lib.utils.extensions.inflate
import com.demo.lib.utils.extensions.loadUrl
import com.demo.tinder.R
import com.demo.tinder.data.entities.User
import kotlinx.android.synthetic.main.item_favourite_user.view.*

class FavouriteUserAdapter :
    PagingDataAdapter<User, FavouriteUserAdapter.ViewHolder>(USER_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_favourite_user))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        getItem(position)?.let { nsUser ->
            holder.bind(nsUser)
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(user: User) {

            itemView.imageViewUserProfile.loadUrl(user.picture)

            user.name?.let {
                itemView.textViewUserTitle.text = "${it.first} ${it.last}"
            }
        }
    }


    companion object {

        val USER_COMPARATOR = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem == newItem
        }
    }
}