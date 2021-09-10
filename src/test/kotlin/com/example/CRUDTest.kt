package com.example
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Matchers
import org.mockito.Mock
import org.mockito.Mockito
import services.Idatabaseclient
import services.mySqlClient
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

@MicronautTest
class CRUDTest {
    @InjectMocks
    lateinit var client : Idatabaseclient//mock interface ,pass the interface
    @Inject
    lateinit var application: EmbeddedApplication<*>


    @Test
    fun testItWorks() {
        Assertions.assertTrue(application.isRunning)
    }

    @Test
    @DisplayName("Checking insert")
    fun testInsertTable() {
        Assertions.assertEquals("Done Successfully", client.insert("mo-dep"))
    }
}
