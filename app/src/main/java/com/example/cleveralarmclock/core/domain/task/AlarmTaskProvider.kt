package com.example.cleveralarmclock.core.domain.task
import com.example.cleveralarmclock.R

object AlarmTaskProvider{

    val allTasks = listOf(
        AlarmTask(
            type = TaskType.SHAKE,
            titleResId = R.string.shake,
            descriptionResId = R.string.shake_your_phone_to_turn_the_alarm_off,
            iconResId = android.R.drawable.ic_menu_preferences // will be changed in the future
        ),
        AlarmTask(
            type = TaskType.CAMERA,
            titleResId = R.string.ai_task,
            descriptionResId = R.string.take_a_photo_of_required_object_to_turn_the_alarm_off,
            iconResId = android.R.drawable.ic_menu_camera // will be changed in the future
        )
    )

    fun getTaskById(id: String): AlarmTask? {
        return allTasks.find { it.type.id == id }
    }
}