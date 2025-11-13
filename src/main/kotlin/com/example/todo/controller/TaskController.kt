package com.example.todo.controller

import com.example.todo.dto.TaskRequest
import com.example.todo.dto.TaskResponse
import com.example.todo.service.TaskService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tasks")
class TaskController(private val taskService: TaskService) {

    @PostMapping
    fun createTask(@RequestBody @Validated taskRequest: TaskRequest): ResponseEntity<TaskResponse> {
        // Call the service to create the task
        val createdTask: TaskResponse = taskService.createTask(taskRequest)

        // Return 201 Created with the TaskResponse
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask)
    }

    @GetMapping
    fun getAllTasks(): ResponseEntity<List<TaskResponse>> {
        val allTasks: List<TaskResponse> = taskService.getAllTasks()

        return ResponseEntity.ok(allTasks)
    }

    @GetMapping("/{id}")
    fun getTaskById(@PathVariable id: String): ResponseEntity<TaskResponse> {
        // Call service to fetch the task
        val task: TaskResponse = try {
            taskService.getTaskById(id)
        } catch (e: IllegalArgumentException) {
            return ResponseEntity.notFound().build() // 404 if task not found
        }

        // Return 200 OK with the task
        return ResponseEntity.ok(task)
    }

    @PutMapping("/{id}")
    fun updateTask(@PathVariable id: String,
                   @RequestBody @Validated taskRequest: TaskRequest): ResponseEntity<TaskResponse> {
        val task: TaskResponse = try {
            taskService.updateTask(id, taskRequest)
        } catch (e: IllegalArgumentException) {
            return ResponseEntity.notFound().build()
        }

        return ResponseEntity.ok(task)
    }

    @DeleteMapping("/{id}")
    fun deleteTask(@PathVariable id: String): ResponseEntity<Void> {
        return try {
            taskService.deleteTask(id)  // deletes the task
            ResponseEntity.noContent().build() // 204 No Content
        } catch (e: IllegalArgumentException) {
            ResponseEntity.notFound().build()  // 404 if task not found
        }
    }
}