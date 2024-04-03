package com.pandora.api.converters

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

internal class ByteArrayConverter : Converter<ResponseBody, ByteArray> {
    override fun convert(value: ResponseBody): ByteArray? = value.bytes()
}

internal class ByteArrayConverterFactory : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, ByteArray> = ByteArrayConverter()

    companion object {
        fun create(): ByteArrayConverterFactory = ByteArrayConverterFactory()
    }
}