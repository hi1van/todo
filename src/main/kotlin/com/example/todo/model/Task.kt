package com.example.todo.model

import java.time.LocalDateTime

data class Task(
    val id: String,
    val title: String,
    val description: String,
    val status: TaskStatus = TaskStatus.TODO,
    val dueDate: LocalDateTime? = null
) {
    enum class TaskStatus {
        TODO, IN_PROGRESS, DONE
    }
}
