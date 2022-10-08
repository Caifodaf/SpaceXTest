package ru.android.spacextest.api.init

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class SerializeNulls

class SerializeNullsFactory : JsonAdapter.Factory {
    override fun create(type: Type, annotations: Set<Annotation?>, moshi: Moshi): JsonAdapter<*>? {
        val nextAnnotations = Types.nextAnnotations(
            annotations,
            SerializeNulls::class.java
        ) ?: return null
        return moshi.nextAdapter<Any>(this, type, nextAnnotations).serializeNulls()
    }
}