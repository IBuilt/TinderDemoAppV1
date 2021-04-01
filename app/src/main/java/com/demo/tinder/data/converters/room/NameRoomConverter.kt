package com.demo.tinder.data.converters.room

import androidx.room.TypeConverter
import com.demo.lib.utils.extensions.toJson
import com.demo.lib.utils.extensions.toJsonObject
import com.demo.lib.utils.extensions.whenNotNull
import com.demo.tinder.data.entities.Name

class NameRoomConverter {

    @TypeConverter
    fun fromString(value: String): Name? {
        return value.toJsonObject()
    }


    @TypeConverter
    fun fromArray(value: Name?): String? {

        whenNotNull(value) {
            return value.toJson()
        }

        return ""
    }
}