package com.example.cleveralarmclock.core.domain.task
import com.example.cleveralarmclock.R

object AlarmTaskProvider{

    val allTasks = listOf(
        AlarmTask(
            type = TaskType.SHAKE,
            titleResId = R.string.shake_task,
            descriptionResId = R.string.shake_your_phone_to_turn_the_alarm_off,
            iconResId = R.drawable.outline_mobile_vibrate
        ),
        AlarmTask(
            type = TaskType.CAMERA,
            titleResId = R.string.ai_task,
            descriptionResId = R.string.take_a_photo_of_required_object_to_turn_the_alarm_off,
            iconResId = R.drawable.outline_photo_camera
        )
    )

    fun getTaskById(id: String): AlarmTask? {
        return allTasks.find { it.type.id == id }
    }
}