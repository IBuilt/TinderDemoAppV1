package com.demo.tinder

import com.demo.tinder.data.entities.Location
import com.demo.tinder.data.entities.Name
import com.demo.tinder.data.entities.User

object TestUser {

    fun findUser(): User {

        return User("8348383", Name("mr", "Hams", "Ken"),
                "male",
                Location("82", "Den", "Mah", 425221),
                "qba",
                99494944,
                "88383838383",
                "no photo")
    }


    fun findUsers(): List<User> {

        return listOf(findUser())
    }
}