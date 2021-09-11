package Controller

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import jakarta.inject.Inject
import model.Department
import services.Idatabaseclient
import java.sql.SQLException

@Controller("/")
class Controller(@Inject val databaseclient: Idatabaseclient) {
    var idatabaseclient:Idatabaseclient

    init {
        this.idatabaseclient=databaseclient
    }

    @Get("/get/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    fun findById(id: String): String {
        return wrapperfunction { idatabaseclient.findById(id) }
    }

    @Get("/getAll")
    @Produces(MediaType.TEXT_PLAIN)
    fun getAll(): String {
        return wrapperfunction { idatabaseclient.getAll() }
    }

    @Post("/insert")
    fun insert(@Body body: String): String {
        return wrapperfunction { idatabaseclient.insert(body) }
    }

    @Put("/update")
    fun update(@Body body: String): String {
        return wrapperfunction { idatabaseclient.update(body) }
    }

    @Delete("/delete")
    fun delete(@Body id: String): String {
        return wrapperfunction { idatabaseclient.delete(id) }
    }

    @Get("/try")
    fun test(@Body dep: Department): String {
        println(dep)
        return wrapperfunction { idatabaseclient.getD(dep) }
    }

    fun wrapperfunction(func: () -> String): String {
        try {
            return func()
        } catch (e: SQLException) {
            return e.toString()
        }
    }
}
