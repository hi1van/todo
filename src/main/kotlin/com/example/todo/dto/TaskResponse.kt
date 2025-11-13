package com.example.todo.dto

import com.example.todo.model.TaskStatus
import java.time.LocalDate

class TaskResponse (
    val id: String,
    val title: String,
    val description: String,
    val status: TaskStatus = TaskStatus.TODO,
    val dueDate: LocalDate? = null
) {}