package Controller

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.http.annotation.Controller
import jakarta.inject.Inject
import services.JWT
import services.iDatabaseClient
import java.sql.SQLException

@Controller("/")
class Controller(@Inject val databaseClient: iDatabaseClient,@Inject val jwt: JWT) {

    @Get("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun find(id: String): String {
        return runAndCatch { databaseClient.getUser(id) }
    }

    @Get("/getAll")
    @Produces(MediaType.TEXT_PLAIN)
    fun getAll(): String {
        return runAndCatch { databaseClient.getAll() }
    }

    @Post("/insert")
    fun insert(@Body body: String): String {
        return runAndCatch { databaseClient.insert(body) }
    }

    @Put("/update")
    fun update(@Body body: String): String {
        return runAndCatch { databaseClient.update(body) }
    }

    @Delete("/delete")
    fun delete(@Body id: String): String {
        return runAndCatch { databaseClient.delete(id) }
    }

    @Post("/login")
    fun login(): String {
        return jwt.getJWT()
    }

    fun runAndCatch(func: () -> String): String {
        try {
            return func()
        } catch (e: SQLException) {
            return e.toString()
        }
    }
}
