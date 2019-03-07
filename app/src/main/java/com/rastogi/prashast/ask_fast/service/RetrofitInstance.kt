package com.rastogi.prashast.ask_fast.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    private val mRetrofit: Retrofit
    private var mTmdbApi: TmdbService? = null

    val tmdbApi: TmdbService
        get() {
            if (mTmdbApi == null) {
                mTmdbApi = mRetrofit.create<TmdbService>(TmdbService::class.java)
            }
            return mTmdbApi!!
        }

    init {
        val client = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        client.addInterceptor(loggingInterceptor)

        mRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client.build())
            .build()
    }

    companion object {
        private val BASE_URL = "https://api.themoviedb.org/3/"
        private var mInstance: RetrofitInstance? = null

        val instance: RetrofitInstance
            get() {
                if (mInstance == null) {
                    mInstance =
                            RetrofitInstance()
                }
                return mInstance!!
            }
    }

}