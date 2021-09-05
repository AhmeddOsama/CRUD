package services

import model.Department

interface Idatabaseclient {
    fun getAll(): String
    fun findById(id: String): String
    fun insert(body: String): String
    fun update(body: String): String
    fun delete(id: String): String
    fun getD(d: Department): String
}
