package gql.network

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import kotlin.LazyThreadSafetyMode.NONE

class OkhttpBuilder {

    private val mediaType = "application/json; charset=utf-8".toMediaType()
    private val okHttpClient by lazy(NONE) { OkHttpClient() }

    fun post(
        url: String,
        json: String,
        onSuccess: (String?) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        val requestBody = json.toRequestBody(mediaType)
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()
        return okHttpClient
            .newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    onFailure(e)
                }
                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        onSuccess(response.body?.string())
                    }
                }
            })
    }

}