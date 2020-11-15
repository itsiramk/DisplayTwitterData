package com.org.twitterdata.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.org.twitterdata.db.AppDatabase
import com.org.twitterdata.db.dao.TwitterDao
import com.org.twitterdata.network.iService
import com.org.twitterdata.remote.ServerDataSource
import com.org.twitterdata.repository.TwitterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(350, TimeUnit.SECONDS)
                .readTimeout(315, TimeUnit.SECONDS)
                .writeTimeout(315, TimeUnit.SECONDS)
                .build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson,client: OkHttpClient) : Retrofit = Retrofit.Builder()
        .baseUrl("https://6f8a2fec-1605-4dc7-a081-a8521fad389a.mock.pstmn.io/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideService(retrofit: Retrofit): iService = retrofit.create(iService::class.java)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideTwitterDao(db: AppDatabase) = db.twitterDao()

    @Singleton
    @Provides
    fun provideRepository(serverDataSource: ServerDataSource,
                          localDataSource: TwitterDao) =
        TwitterRepository(localDataSource,serverDataSource)
}