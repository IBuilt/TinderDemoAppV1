package com.demo.tinder.ui.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demo.lib.utils.extensions.loadUrl
import com.demo.tinder.R
import com.demo.tinder.data.entities.User
import com.demo.tinder.utils.PrepareUserInformation
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter : RecyclerView.Adapter<UserAdapter.TinderViewHolder>() {

    private var tinderUserList = emptyList<User>()


    fun applyList(tinderUsers: List<User>) {
        this.tinderUserList = tinderUsers
    }


    fun getUsers() = tinderUserList


    fun getTopUser() = if (tinderUserList.isNotEmpty()) tinderUserList[0] else null


    fun isUserAvailableMoreThanOne() = tinderUserList.size > 1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TinderViewHolder {
        return TinderViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, null, false)
        )
    }


    override fun onBindViewHolder(holder: TinderViewHolder, position: Int) {
        holder.bind(tinderUserList[position])
    }


    override fun getItemCount(): Int {
        return tinderUserList.size
    }


    inner class TinderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        init {
            itemView.btnProfile.setOnClickListener(this)
            itemView.btnAddress.setOnClickListener(this)
            itemView.btnDob.setOnClickListener(this)
            itemView.btnPhone.setOnClickListener(this)
            itemView.btnRegistered.setOnClickListener(this)
        }


        override fun onClick(view: View?) {

            view?.let {

                val (header, description) = PrepareUserInformation.get(
                    it.context,
                    it.id,
                    tinderUserList[absoluteAdapterPosition]
                )


                itemView.textViewHeader.text = header

                itemView.textViewDescription.text = description
            }
        }


        fun bind(tinderUser: User) {

            tinderUser.apply {

                itemView.imageViewUserPicture.loadUrl(tinderUser.picture)

                itemView.btnAddress.isChecked = true
                itemView.btnAddress.callOnClick()
            }
        }
    }
}