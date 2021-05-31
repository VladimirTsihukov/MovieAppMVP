package com.androidapp.movieappmvp.di.modules

import com.androidapp.movieappmvp.App
import com.androidapp.movieappmvp.model.api.ApiService
import com.androidapp.movieappmvp.model.newtwork.INetworkStatus
import com.androidapp.movieappmvp.ui.network.NetworkStatus
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApiModule {

    private val languageRU = "ru"
    private val languageENG = "US"

    @Named("BASE_URL_MOVIE")
    @Provides
    fun baseUrl(): String = "https://api.themoviedb.org/3/movie/"

    @Named("API_KEY_ID")
    @Provides
    fun apiKeyID(): String = "7d9db2e12493542315f5bcb0f3f0de61"

    @Named("API_KEY")
    @Provides
    fun apiKey(): String = "api_key"

    @Named("QUERY_PARAM_LANGUAGE")
    @Provides
    fun queryParamLanguage(): String = "language"

    @Named("LANGUAGE")
    @Provides
    fun languageRus(): String = if (Locale.getDefault().language == "ru") languageRU else languageENG

    @Singleton
    @Provides
    fun authInterceptor(
        @Named("API_KEY") apiKey: String,
        @Named("API_KEY_ID") apiKeyID: String,
        @Named("QUERY_PARAM_LANGUAGE") queryParamLanguage: String,
        @Named("LANGUAGE") languageRus: String
    ): Interceptor = Interceptor { chain ->
        val newUrl = chain.request().url()
            .newBuilder()
            .addQueryParameter(apiKey, apiKeyID)
            .addQueryParameter(queryParamLanguage, languageRus)
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }

    @Singleton
    @Provides
    fun tmdClient(authInterceptor: Interceptor): OkHttpClient =
        OkHttpClient().newBuilder()
            .addInterceptor(authInterceptor)
            .build()

    @Singleton
    @Provides
    fun retrofitMovie(tmdClient: OkHttpClient, @Named("BASE_URL_MOVIE") baseUrl: String): Retrofit =
        Retrofit.Builder()
            .client(tmdClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(baseUrl)
            .build()

    @Singleton
    @Provides
    fun apiServiceMovies(retrofitMovie: Retrofit): ApiService =
        retrofitMovie.create(ApiService::class.java)

    @Singleton
    @Provides
    fun networkStatus(app: App): INetworkStatus = NetworkStatus(app)
}