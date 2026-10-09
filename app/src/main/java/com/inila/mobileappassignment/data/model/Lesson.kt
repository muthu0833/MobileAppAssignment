package com.inila.mobileappassignment.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "lessons",
    foreignKeys = [
        ForeignKey(
            entity = Course::class,
            parentColumns = ["id"],
            childColumns = ["courseId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Lesson(
    @PrimaryKey val id: String,
    val courseId: String,
    val title: String,
    val isCompleted: Boolean
)
