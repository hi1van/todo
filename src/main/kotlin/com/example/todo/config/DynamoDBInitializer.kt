package com.example.todo.config

import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.*

@Configuration
class DynamoDBInitializer(private val dynamoDbClient: DynamoDbClient) {

    @PostConstruct
    fun createTables() {
        val tableName = "Tasks"

        // Check if table already exists
        val existingTables = dynamoDbClient.listTables().tableNames()
        if (existingTables.contains(tableName)) return

        val createTableRequest = CreateTableRequest.builder()
            .tableName(tableName)
            .keySchema(
                KeySchemaElement.builder()
                    .attributeName("id")
                    .keyType(KeyType.HASH)
                    .build()
            )
            .attributeDefinitions(
                AttributeDefinition.builder()
                    .attributeName("id")
                    .attributeType(ScalarAttributeType.S)
                    .build()
            )
            .provisionedThroughput(
                ProvisionedThroughput.builder()
                    .readCapacityUnits(5)
                    .writeCapacityUnits(5)
                    .build()
            )
            .build()

        dynamoDbClient.createTable(createTableRequest)
        println("DynamoDB table '$tableName' created!")
    }
}
