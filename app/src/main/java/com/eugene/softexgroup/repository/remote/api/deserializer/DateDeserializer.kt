package com.eugene.softexgroup.repository.remote.api.deserializer

import com.eugene.softexgroup.utils.SERVER_DATE_FORMAT
import com.eugene.softexgroup.utils.parseDate
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.util.*

/**
 * Десериализация типа Date
 */
class DateDeserializer: JsonDeserializer<Date> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Date? {
        if (json != null) {
            val date = json.asString
            return parseDate(date, SERVER_DATE_FORMAT)
        }
        return null
    }


}