package com.demo.tinder.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class User(

    var username: String,
    var name: Name? = null,
    var gender: String,
    var location: Location? = null,
    var password: String,
    var dob: Int,
    var phone: String,
    var picture: String
) {
    @field: PrimaryKey(autoGenerate = true)
    var id: Int = 0
}