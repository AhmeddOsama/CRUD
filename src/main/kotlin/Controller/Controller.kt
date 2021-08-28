package Controller

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import services.Services
import io.micronaut.http.annotation.*
@Controller("/")
class Controller {
    @Get("/")
    @Produces(MediaType.TEXT_PLAIN)
    fun index():String{
        //  println(Get)
        var s = Services.connection()
        //println(s)
        return s
    }

    @Get("/get/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    fun findById(id: Int): String {
        return Services.findById(id)
    }
    @Get("/getAll")
    @Produces(MediaType.TEXT_PLAIN)
    fun getAll():String
    {
        return Services.getAll()
    }
    @Post("/save")
    fun save(@Body body:String):String{
        return Services.add(body)
    }
    @Put("/update")
    fun update(@Body body:String): String{
        return Services.update(body)
    }
    @Delete("/delete")
    fun deleteById(@Body id: String): String {

        return Services.deleteById(id)
    }


}
