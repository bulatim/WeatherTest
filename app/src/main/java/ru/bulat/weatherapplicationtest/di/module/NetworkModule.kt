package ru.bulat.weatherapplicationtest.di.module

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.bulat.weatherapplicationtest.BuildConfig
import ru.bulat.weatherapplicationtest.di.scope.AppScope
import ru.bulat.weatherapplicationtest.network.WeatherApi
import ru.bulat.weatherapplicationtest.utils.BASE_URL


@Module(includes = [ContextModule::class])
class NetworkModule {
    @AppScope
    @Provides
    internal fun provideOkHttpClient(): OkHttpClient {
        val okHttpBuilder = OkHttpClient().newBuilder()
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
            okHttpBuilder.addInterceptor(logging)
        }
        return okHttpBuilder.build()
    }

    @AppScope
    @Provides
    internal fun providePostApi(retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }

    @AppScope
    @Provides
    internal fun provideRetrofitInterface(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }
}