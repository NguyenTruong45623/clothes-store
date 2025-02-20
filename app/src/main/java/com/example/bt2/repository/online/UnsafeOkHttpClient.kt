package com.example.bt2.repository.online

import okhttp3.OkHttpClient
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.*

object UnsafeOkHttpClient {
    fun getUnsafeOkHttpClient(): OkHttpClient {
        return try {
            // Tạo một TrustManager chấp nhận tất cả chứng chỉ
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            })

            // Khởi tạo SSL context với TrustManager mới
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, trustAllCerts, SecureRandom())

            // Tạo SSLSocketFactory từ SSLContext
            val sslSocketFactory = sslContext.socketFactory

            OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                .hostnameVerifier { _, _ -> true } // Bỏ qua kiểm tra hostname
                .build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
