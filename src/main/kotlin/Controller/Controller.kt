package Controller

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import jakarta.inject.Inject
import services.iDatabaseClient
import java.sql.SQLException

@Controller("/")
class Controller(@Inject val databaseClient: iDatabaseClient) {

    @Get("/get")
    @Produces(MediaType.TEXT_PLAIN)
    fun find(id: String): String {
        return wrapperfunction { databaseClient.find(id) }
    }

    @Get("/getAll")
    @Produces(MediaType.TEXT_PLAIN)
    fun getAll(): String {
        return wrapperfunction { databaseClient.getAll() }
    }

    @Post("/insert")
    fun insert(@Body body: String): String {
        return wrapperfunction { databaseClient.insert(body) }
    }

    @Put("/update")
    fun update(@Body body: String): String {
        return wrapperfunction { databaseClient.update(body) }
    }

    @Delete("/delete")
    fun delete(@Body id: String): String {
        return wrapperfunction { databaseClient.delete(id) }
    }

    fun wrapperfunction(func: () -> String): String {
        try {
            return func()
        } catch (e: SQLException) {
            return e.toString()
        }
    }
}
