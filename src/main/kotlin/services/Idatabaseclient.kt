package services

import model.Department

interface Idatabaseclient {
    fun connection(): String
    fun getAll(): String
    fun findById(id: String): String
    fun add(body: String): String
    fun update(body: String): String
    fun deleteById(id: String): String
    fun getD(d: Department): String
}
