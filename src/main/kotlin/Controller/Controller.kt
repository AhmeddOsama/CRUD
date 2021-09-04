package Controller

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import model.Department
import services.mySqlClient
import java.sql.SQLException

@Controller("/")
class Controller {
    val sqlclient = mySqlClient()

    @Get("/help")
    @Produces(MediaType.TEXT_PLAIN)
    fun index(): String {
        return wrapperfunction { sqlclient.connection() }
    }
    @Get("/get/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    fun findById(id: String): String {
        return sqlclient.findById(id)
    }
    @Get("/getAll")
    @Produces(MediaType.TEXT_PLAIN)
    fun getAll(): String {
        return sqlclient.getAll()
    }
    @Post("/save")
    fun save(@Body body: String): String {
        return sqlclient.add(body)
    }
    @Put("/update")
    fun update(@Body body: String): String {
        return sqlclient.update(body)
    }
    @Delete("/delete")
    fun deleteById(@Body id: String): String {
        return sqlclient.delete(id)
    }
    @Get("/try")
    fun test(@Body dep: Department): String {
        println(dep)
        return sqlclient.getD(dep)
    }
    fun wrapperfunction(func: () -> String): String {
        try {
            return func()
        } catch (e: SQLException) {
            return e.printStackTrace().toString()
        }
    }
}
