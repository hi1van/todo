package com.example.todo.dto

import com.example.todo.model.TaskStatus
import java.time.LocalDateTime

class TaskRequest (
    val title: String,
    val description: String,
    val status: TaskStatus = TaskStatus.TODO,
    val dueDate: LocalDateTime? = null
){}