package services

import model.Department
import java.io.*
import java.sql.*
import java.util.*

class mySqlClient : Idatabaseclient {
    lateinit var connection: Connection
    lateinit var query: ResultSet
    lateinit var result: String

    fun readConf(): String {
        var filename = "dbinfo.ini"
        var properties = Properties()

        try {
            val fis = FileInputStream(filename)
            properties.load(fis)
            return properties.getProperty("url")
        } catch (ex: FileNotFoundException) {
            return "FileNotFound"
        } catch (ex: IOException) {
            return "IO Exception"
        }
    }
    override fun connection(): String {

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/lab4?serverTimezone=UTC", "root", "")
            return "Database Connected Successfully"
        } catch (sqlError: SQLException) {
            sqlError.printStackTrace()
            return "Failure"
        }
    }
    override fun findById(id: String): String {
        if (connection() == "Failure")
            return "Failed to connect to Database"
        var statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        query = statement.executeQuery("SELECT * FROM `departments` WHERE dno=" + id)
        query.next()
        result = query.getString("dno") + "-" + query.getString("dname") + "" + "-" + query.getString("manager")
        return result
    }
    override fun getAll(): String {

        if (connection() == "Failure")
            return "Failed to connect to Database"
        var statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        query = statement.executeQuery("SELECT * FROM `departments`")
        query.next()
        result = ""
        while (query.isLast == false) {
            result = result + query.getString("dno") + "-" + query.getString("dname") + "-" + query.getString("manager") + "\n"
            query.next()
        }
        return result
    }
    override fun add(body: String): String {
        if (connection() == "Failure")
            return "Failed to connect to Database"
        val information = body.split("-")
        var statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        println("${information[0]}")
        var query = statement.executeUpdate("INSERT INTO `departments`(`dno`, `dname`, `manager`) VALUES ('${information[0]}','${information[1]}','${information[2]}')")
        connection()
        return "Done Successfully"
    }
    override fun update(body: String): String {
        val information = body.split("-")
        val id = information[0]
        var statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        var query = statement.executeUpdate("UPDATE `departments` SET `dname`='${information[1]}',`manager`='${information[2]}' WHERE dno=" + id)
        connection()
        return "UPDATE SUCCESSFUL"
    }
    override fun delete(id: String): String {
        var statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        var query = statement.executeUpdate("DELETE FROM `departments` WHERE dno=" + id)
        connection()
        return "Delete Success!"
    }
    override fun getD(d: Department): String {
        if (d.dno == null)
            return "NULL"
        else {
            connection()
            var statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
            var query = statement.executeQuery("SELECT * FROM `departments` WHERE dno=" + d.dno)
            result = query.getString("dno") + "-" + query.getString("dname") + "" + "-" + query.getString("manager")
            return result
        }
    }
}
