package com.example.todo.model

import java.time.LocalDate

data class Task(
    val id: String,
    var title: String,
    var description: String,
    var status: TaskStatus = TaskStatus.TODO,
    var dueDate: LocalDate? = null
) {}
