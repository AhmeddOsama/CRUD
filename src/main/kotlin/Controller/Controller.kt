package Controller

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import model.Department
import services.mySqlClient

@Controller("/")
class Controller {
    @Get("/help")
    @Produces(MediaType.TEXT_PLAIN)
    fun index(): String {
        return mySqlClient.connection()
    }
    @Get("/get/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    fun findById(id: String): String {
        return mySqlClient.findById(id)
    }
    @Get("/getAll")
    @Produces(MediaType.TEXT_PLAIN)
    fun getAll(): String {
        return mySqlClient.getAll()
    }
    @Post("/save")
    fun save(@Body body: String): String {
        return mySqlClient.add(body)
    }
    @Put("/update")
    fun update(@Body body: String): String {
        return mySqlClient.update(body)
    }
    @Delete("/delete")
    fun deleteById(@Body id: String): String {

        return mySqlClient.deleteById(id)
    }
    @Get("/try")
    fun test(@Body dep: Department): String {
        println(dep)
        return mySqlClient.getD(dep)
    }
}
