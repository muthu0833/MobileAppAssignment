package com.inila.mobileappassignment.di

import com.inila.mobileappassignment.data.remote.CourseApiService
import com.inila.mobileappassignment.data.remote.CourseRemoteDataSource
import com.inila.mobileappassignment.data.remote.FakeCourseRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://mock-api.example.com/") 
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideCourseApiService(retrofit: Retrofit): CourseApiService {
        return retrofit.create(CourseApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCourseRemoteDataSource(): CourseRemoteDataSource {
        // Here we provide the mock implementation. 
        // In the future, we could easily swap this for a real implementation that uses CourseApiService.
        return FakeCourseRemoteDataSource()
    }
}
