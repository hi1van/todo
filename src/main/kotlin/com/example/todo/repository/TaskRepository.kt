package com.example.todo.repository

import com.example.todo.model.Task
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class TaskRepository {

    fun saveTask(task: Task) {}
    fun getTaskById(id: String) {}
    fun getAllTasks() {}
    fun updateTask(task: Task) {}
    fun deleteTask(id: String) {}
    fun getTasksByStatus(status: Task.TaskStatus) {}
    fun getTasksByDueDate(dueDate: LocalDate) {}
    fun existsById(id: String) {}

}