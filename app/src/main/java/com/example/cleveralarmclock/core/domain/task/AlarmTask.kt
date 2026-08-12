package com.example.cleveralarmclock.core.domain.task

enum class TaskType(val id: String) {
    CAMERA("camera"),
    SHAKE("shake")
}

data class AlarmTask(
    val type: TaskType,
    val titleResId: Int,       // Link to string resource
    val descriptionResId: Int,
    val iconResId: Int
)