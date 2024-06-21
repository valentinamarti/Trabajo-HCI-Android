package com.example.itba.hci.utils

import com.google.gson.*
import java.lang.reflect.Type

class AnyTypeAdapter : JsonDeserializer<Any?> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Any? {
        return when {
            json.isJsonNull -> null
            json.isJsonPrimitive -> {
                val primitive = json.asJsonPrimitive
                when {
                    primitive.isNumber -> primitive.asInt
                    primitive.isString -> primitive.asString
                    else -> null
                }
            }
            else -> null
        }
    }
}
