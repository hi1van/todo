package com.example.todo.repository

import com.example.todo.model.Task
import com.example.todo.model.TaskStatus
import org.springframework.stereotype.Repository
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest
import software.amazon.awssdk.services.dynamodb.model.ScanRequest

@Repository
class TaskRepository(private val dynamoDbClient: DynamoDbClient) {

    private val tableName = "Tasks"

    // Save or update a task
    fun saveTask(task: Task) {
        val item = mapOf(
            "id" to AttributeValue.builder().s(task.id).build(),
            "title" to AttributeValue.builder().s(task.title).build(),
            "description" to AttributeValue.builder().s(task.description ?: "").build(),
            "status" to AttributeValue.builder().s(task.status.name).build(),
            "dueDate" to AttributeValue.builder().s(task.dueDate?.toString() ?: "").build()
        )

        dynamoDbClient.putItem { it.tableName(tableName).item(item) }
    }

    // Get a task by ID
    fun getTaskById(id: String): Task? {
        val request = GetItemRequest.builder()
            .tableName(tableName)
            .key(mapOf("id" to AttributeValue.builder().s(id).build()))
            .build()

        val result = dynamoDbClient.getItem(request)
        return if (result.hasItem()) mapToTask(result.item()) else null
    }

    // Get all tasks
    fun getAllTasks(): List<Task> {
        val result = dynamoDbClient.scan { it.tableName(tableName) }
        return result.items().map { mapToTask(it) }
    }

    // Delete a task by ID
    fun deleteTask(id: String) {
        val request = DeleteItemRequest.builder()
            .tableName(tableName)
            .key(mapOf("id" to AttributeValue.builder().s(id).build()))
            .build()

        dynamoDbClient.deleteItem(request)
    }

    // Get tasks filtered by status
    fun getTasksByStatus(status: TaskStatus): List<Task> {
        val scanRequest = ScanRequest.builder()
            .tableName(tableName)
            .filterExpression("#s = :statusValue")
            .expressionAttributeNames(mapOf("#s" to "status"))
            .expressionAttributeValues(mapOf(":statusValue" to AttributeValue.builder().s(status.name).build()))
            .build()

        val result = dynamoDbClient.scan(scanRequest)
        return result.items().map { mapToTask(it) }
    }

    // Helper to convert DynamoDB item to Task object
    private fun mapToTask(item: Map<String, AttributeValue>): Task {
        return Task(
            id = item["id"]!!.s(),
            title = item["title"]!!.s(),
            description = item["description"]!!.s(),
            status = TaskStatus.valueOf(item["status"]!!.s()),
            dueDate = item["dueDate"]?.s()?.takeIf { it.isNotEmpty() }?.let { java.time.LocalDate.parse(it) }
        )
    }
}