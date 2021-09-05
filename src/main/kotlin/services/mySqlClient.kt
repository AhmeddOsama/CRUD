package services

import model.Department
import java.io.*
import java.sql.*
import java.util.*

class mySqlClient : Idatabaseclient {
    lateinit var query: ResultSet
    lateinit var result: String
    var prop = readConf()
    var connection = DriverManager.getConnection(prop["url"].toString(), prop["username"].toString(), prop["password"].toString())

    fun readConf(): Properties {
        var filename = "dbinfo.ini"
        var properties = Properties()
        val fis = FileInputStream(filename)
        properties.load(fis)
        return properties
    }
    override fun findById(id: String): String {
        var statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        query = statement.executeQuery("SELECT * FROM `departments` WHERE dno=" + id)
        query.next()
        result = query.getString("dno") + "-" + query.getString("dname") + "" + "-" + query.getString("manager")
        return result
    }
    override fun getAll(): String {
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
    override fun insert(body: String): String {
        val information = body.split("-")
        var statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        println("${information[0]}")
        var query = statement.executeUpdate("INSERT INTO `departments`(`dno`, `dname`, `manager`) VALUES ('${information[0]}','${information[1]}','${information[2]}')")
        return "Done Successfully"
    }
    override fun update(body: String): String {
        val information = body.split("-")
        var statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        var query = statement.executeUpdate("UPDATE `departments` SET `dname`='${information[1]}',`manager`='${information[2]}' WHERE dno=${information[0]}")
        return "UPDATE SUCCESSFUL"
    }
    override fun delete(id: String): String {
        var statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        var query = statement.executeUpdate("DELETE FROM `departments` WHERE dno=" + id)
        return "Delete Success!"
    }
    override fun getD(d: Department): String {
        var statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        var query = statement.executeQuery("SELECT * FROM `departments` WHERE dno=" + d.dno)
        result = query.getString("dno") + "-" + query.getString("dname") + "" + "-" + query.getString("manager")
        return result
    }
}
