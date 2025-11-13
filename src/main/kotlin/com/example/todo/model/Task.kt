package com.example.todo.model

import java.time.LocalDateTime

data class Task(
    val id: String,
    var title: String,
    var description: String,
    var status: TaskStatus = TaskStatus.TODO,
    var dueDate: LocalDateTime? = null
) {}
