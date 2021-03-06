package com.websarva.wings.android.asynctestcoroui

import okhttp3.OkHttpClient
import okhttp3.Request

object HttpClient {
    //OKHttp3はシングルトンで使う
    val instance = OkHttpClient()
}

class HttpUtil {
    fun httpGet(url: String): String? {
        val request = Request.Builder()
            .url(url)
            .build()

        val response = HttpClient.instance.newCall(request).execute()
        val body = response.body?.string()
        return body
    }
}