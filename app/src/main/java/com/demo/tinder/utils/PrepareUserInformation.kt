package com.demo.tinder.utils

import android.content.Context
import com.demo.lib.utils.DateUtils
import com.demo.tinder.R
import com.demo.tinder.data.entities.User

object PrepareUserInformation {

    fun get(context : Context, id: Int, user: User): Pair<String, String> {

        return when (id) {

            R.id.btnProfile -> {

                var name = ""

                user.name?.let {
                    name = "${it.title} ${it.first} ${it.last}"
                }

                Pair(context.getString(R.string.string_my_string), name)
            }

            R.id.btnAddress -> {

                var location = ""

                user.location?.let {
                    location = "${it.street}, ${it.city}, ${it.state}"
                }

                Pair(context.getString(R.string.string_my_address), location)
            }


            R.id.btnPhone -> {
                Pair(context.getString(R.string.string_my_cell), user.phone)
            }


            R.id.btnDob -> {

                val strDate = DateUtils.getDate(user.dob.toLong())

                Pair(context.getString(R.string.string_my_dob), strDate!!)
            }

            R.id.btnRegistered -> {

                Pair(context.getString(R.string.string_my_password), user.password)
            }

            else -> {

                Pair("", "")
            }
        }
    }
}