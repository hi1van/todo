package com.example.todo.service

import com.example.todo.dto.TaskRequest
import com.example.todo.dto.TaskResponse
import com.example.todo.model.Task
import com.example.todo.model.TaskStatus
import com.example.todo.repository.TaskRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class TaskService (private val taskRepository: TaskRepository) {

    fun createTask(taskRequest: TaskRequest): TaskResponse {
        val taskId = UUID.randomUUID().toString()

        val task = Task(
            id = taskId,
            title = taskRequest.title,
            description = taskRequest.description,
            status = taskRequest.status,
            dueDate = taskRequest.dueDate
        )

        taskRepository.saveTask(task)

        return TaskResponse(
            id = task.id,
            title = task.title,
            description = task.description,
            status = task.status,
            dueDate = task.dueDate
        )
    }

    fun getAllTasks(): List<TaskResponse> {
        val tasks = taskRepository.getAllTasks()

        return tasks.map { task ->
            TaskResponse(
                id = task.id,
                title = task.title,
                description = task.description,
                status = task.status,
                dueDate = task.dueDate
            )
        }
    }

    fun getTaskById(id: String): TaskResponse {
        val task = taskRepository.getTaskById(id)
            ?: throw IllegalArgumentException("Task with id $id not found")

        return TaskResponse(
            id = task.id,
            title = task.title,
            description = task.description,
            status = task.status,
            dueDate = task.dueDate
        )
    }

    fun updateTask(id: String, taskRequest: TaskRequest): TaskResponse {
        val existingTask: Task = taskRepository.getTaskById(id)
            ?: throw IllegalArgumentException("Task with id $id not found")

        existingTask.title = taskRequest.title
        existingTask.description = taskRequest.description
        existingTask.status = taskRequest.status
        existingTask.dueDate = taskRequest.dueDate

        taskRepository.saveTask(existingTask)

        return TaskResponse(
            id = existingTask.id,
            title = existingTask.title,
            description = existingTask.description,
            status = existingTask.status,
            dueDate = existingTask.dueDate
        )
    }

    fun deleteTask(id: String): Boolean {
        val existingTask = taskRepository.getTaskById(id)

        return if (existingTask != null) {
            taskRepository.deleteTask(id)
            true
        } else {
            false
        }
    }

    fun getTasksByStatus(status: TaskStatus): List<TaskResponse> {
        val tasksByStatus: List<Task> = taskRepository.getTasksByStatus(status)

        return tasksByStatus.map {task ->
            TaskResponse(
                id = task.id,
                title = task.title,
                description = task.description,
                status = task.status,
                dueDate = task.dueDate
            )
        }
    }
}