package com.application.traveldiary.adapter

import android.net.Uri
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type


class UriTypeAdapter : JsonSerializer<Uri?>, JsonDeserializer<Uri?> {

    @Throws(JsonParseException::class)
    override fun deserialize(
        src: JsonElement, srcType: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        // Convert "https://phantomvk.com" to https://phantomvk.com
        return Uri.parse(src.toString().replace("\"", ""))
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}
