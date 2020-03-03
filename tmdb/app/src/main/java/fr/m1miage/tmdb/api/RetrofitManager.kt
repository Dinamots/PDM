package fr.m1miage.tmdb.api

import fr.m1miage.tmdb.api.rest.TmdbAPI
import fr.m1miage.tmdb.utils.API_KEY
import fr.m1miage.tmdb.utils.API_URL
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitManager {

    companion object {
        val tmdbAPI: TmdbAPI = getInstance().create(TmdbAPI::class.java)

        private fun getInstance(): Retrofit {
            val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
            httpClient.addInterceptor { chain -> getUpdatedChain(chain) }
            return Retrofit.Builder()
                .client(httpClient.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(API_URL)
                .build()
        }

        private fun getUpdatedChain(chain: Interceptor.Chain): Response {
            val originalRequest: Request = chain.request();
            val originalHttpUrl: HttpUrl = originalRequest.url;
            val url: HttpUrl = originalHttpUrl
                .newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .addQueryParameter("language", "fr")
                .build()
            val requestBuilder: Request.Builder = originalRequest.newBuilder().url(url)
            val newRequest: Request = requestBuilder.build()
            return chain.proceed(newRequest)
        }
    }
}