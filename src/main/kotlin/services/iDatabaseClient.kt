package services

import model.Department

interface iDatabaseClient {
    fun getAll(): String
    fun getUser(id: String): String
    fun insert(body: String): String
    fun update(body: String): String
    fun delete(id: String): String
}
