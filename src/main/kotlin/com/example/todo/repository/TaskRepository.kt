package com.example.todo.repository

import com.example.todo.model.Task
import com.example.todo.model.TaskStatus
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class TaskRepository {

    fun saveTask(task: Task) {}
    fun getTaskById(id: String): Task? {}
    fun getAllTasks(): List<Task> {}
    fun deleteTask(id: String) {}
    fun getTasksByStatus(status: TaskStatus): List<Task> {}
    fun getTasksByDueDate(dueDate: LocalDate) {}
    fun existsById(id: String) {}

}