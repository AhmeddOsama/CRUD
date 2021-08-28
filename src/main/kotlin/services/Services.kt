package services

import io.micronaut.http.annotation.Body
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Connection;

object Services {
   // @JvmStatic
    lateinit var  c: Connection
   // val firstName: String = "Chike"
    fun connection():String {
        try {
          //  println("Hello")
            c = DriverManager.getConnection("jdbc:mysql://localhost/lab4?serverTimezone=UTC","root","")
            return "Database Connected Successfully"
        }
        catch (e: SQLException){
            e.printStackTrace()
            return "Failure"
        }
        return "HELLO"
    }
    fun findById(id:Int):String{
        if(connection()=="Failure")
            return "Failed to connect to Database"
        var s = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        var r = s.executeQuery("SELECT * FROM `departments` WHERE dno="+id)
        r.next()
        var result = r.getString("dno")+"-"+r.getString("dname")+""+"-"+r.getString("manager")
        return  result

    }
    fun getAll():String{
        if(connection()=="Failure")
            return "Failed to connect to Database"
        var s = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        var r = s.executeQuery("SELECT * FROM `departments`")
        var result :String
        r.next()
        result=""
        while(r.isLast==false)
        {
                result=result+r.getString("dno")+"-"+r.getString("dname")+"-"+r.getString("manager")+"\n"
                r.next()
            }
        return result
    }
  fun add(body:String):String
  {
      if(connection()=="Failure")
          return "Failed to connect to Database"
      val information=body.split("-")
      var s = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
      println("${information[0]}")
      var r = s.executeUpdate("INSERT INTO `departments`(`dno`, `dname`, `manager`) VALUES ('${information[0]}','${information[1]}','${information[2]}')")
      //val colorsArray = str.split("-")
        connection()
      return "Done Successfully"
  }
    fun update(body:String):String{
        val information=body.split("-")
        val id = information[0]
        var s = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        println("updating")
        var r = s.executeUpdate("UPDATE `departments` SET `dname`='${information[1]}',`manager`='${information[2]}' WHERE dno="+id)
        connection()
        return "UPDATE SUCCESSFUL"
    }
    fun deleteById(id:String):String
    {
        var s = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        var r = s.executeUpdate("DELETE FROM `departments` WHERE dno="+id)
        connection()
        return "Delete Success!"

    }


}