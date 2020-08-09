package gql.network

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import kotlin.LazyThreadSafetyMode.NONE

class OkhttpBuilder {

    private val mediaType = "application/json; charset=utf-8".toMediaType()
    private val okHttpClient by lazy(NONE) { OkHttpClient() }

    fun post(url: String, json: String): String? {
        val requestBody = json.toRequestBody(mediaType)
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()
        return okHttpClient
            .newCall(request)
            .execute()
            .body?.string()
    }

}