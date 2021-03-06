package services

import jakarta.inject.Singleton
import model.Department
import org.json.JSONObject
import java.io.*
import java.sql.*
import java.util.*

@Singleton
class mySqlClient : iDatabaseClient {
    lateinit var query: ResultSet
    lateinit var result: String
    lateinit var jsonResponse : JSONObject
    var connection = Connect()
    var jsonArray = JSONObject()

    fun Connect(): Connection{
        return  DriverManager.getConnection(System.getenv("DATABASE_URL"), System.getenv("DATABASE_USERNAME"), System.getenv("DATABASE_PASSWORD"))
    }

    override fun getUser(id: String): String {
        jsonResponse = JSONObject()
        var statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        query = statement.executeQuery("SELECT * FROM `departments` WHERE dno=" + id)
        query.next()
        result = query.getString("dno") + "-" + query.getString("dname") + "" + "-" + query.getString("manager")
        jsonResponse.put("id",query.getString("dno"))
        jsonResponse.put("dname", query.getString("dname"))
        jsonResponse.put("manager", query.getString("manager"))
        return jsonResponse.toString()
    }

    override fun getAll(): String {
        var statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        query = statement.executeQuery("SELECT * FROM `departments`")
        query.next()
        result = ""
        while (query.isLast == false) {
            result = result + query.getString("dno") + "-" + query.getString("dname") + "-" + query.getString("manager") + "\n"
            var jsonResponse = JSONObject()
            jsonResponse.put("id",query.getString("dno"))
            jsonResponse.put("dname", query.getString("dname"))
            jsonResponse.put("manager", query.getString("manager"))
            jsonArray.append("Results",jsonResponse)
            query.next()
        }
        return jsonArray.toString()
    }

    override fun insert(body: String): String {
        val information = body.split("-")
        var statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
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

}
