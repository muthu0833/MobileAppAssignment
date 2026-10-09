package com.inila.mobileappassignment.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class Course(
    @PrimaryKey val id: String,
    val title: String,
    val instructor: String,
    val progress: Int,
    val lessonsCount: Int
)
