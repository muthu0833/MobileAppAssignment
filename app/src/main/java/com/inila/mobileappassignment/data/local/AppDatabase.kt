package com.inila.mobileappassignment.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.inila.mobileappassignment.data.model.Course
import com.inila.mobileappassignment.data.model.Lesson

@Database(entities = [Course::class, Lesson::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao
    abstract fun lessonDao(): LessonDao
}
