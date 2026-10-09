package com.inila.mobileappassignment.di

import android.content.Context
import androidx.room.Room
import com.inila.mobileappassignment.data.local.AppDatabase
import com.inila.mobileappassignment.data.local.CourseDao
import com.inila.mobileappassignment.data.local.LessonDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DB_NAME = "learning_dashboard.db"

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DB_NAME
        )
        .fallbackToDestructiveMigration(true)
        .build()
    }

    @Provides
    fun provideCourseDao(database: AppDatabase): CourseDao = database.courseDao()

    @Provides
    fun provideLessonDao(database: AppDatabase): LessonDao = database.lessonDao()
}
