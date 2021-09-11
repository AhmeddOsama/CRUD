package com.example
import Controller.Controller
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
import org.mockito.MockitoAnnotations;

@MicronautTest
class CRUDTest {
    @Mock
    lateinit var client : Idatabaseclient
    lateinit var controller: Controller
    @Inject
    lateinit var application: EmbeddedApplication<*>

   @BeforeEach
   fun initialize()
   {
       MockitoAnnotations.initMocks(this);
       controller = Controller(client)
   }

    @Test
    fun testItWorks() {
        Assertions.assertTrue(application.isRunning)
    }

    @Test
    @DisplayName("Checking InsertValid")
    fun testInsertValid() {
        Mockito.`when`(client.insert("mo-dep")).thenReturn("Done Successfully")
        Assertions.assertEquals("Done Successfully",controller.insert("mo-dep"))
    }

    @Test
    @DisplayName("Checking InsertInvalid")
    fun testInsertInvalid()
    {
        val e = SQLException()
        Mockito.`when`(client.insert("")).thenReturn(e.toString())
        Assertions.assertEquals(e.toString(),controller.insert(""))
    }

    @Test
    @DisplayName("testing getAll")
    fun testGetAll()
    {
        Mockito.`when`(client.getAll()).thenReturn("")
        Assertions.assertEquals("",controller.getAll())
    }

    @Test
    @DisplayName("Checking Update Valid")
    fun testUpdateValid()
    {
        Mockito.`when`(client.update("")).thenReturn("success")
        Assertions.assertEquals("success",controller.update(""))
    }

    @Test
    @DisplayName("Checking Update Invalid")
    fun testUpdateInvalid()
    {
        val e = SQLException()
        Mockito.`when`(client.update("")).thenReturn(e.toString())
        Assertions.assertEquals(e.toString(),controller.update(""))
    }

    @Test
    @DisplayName("Checking delete valid")
    fun testDeleteValid()
    {
        Mockito.`when`(client.delete("")).thenReturn("done")
        Assertions.assertEquals("done",controller.delete(""))
    }

    @Test
    @DisplayName("Checking delete valid")
    fun testDeleteInvalid()
    {
        val e = SQLException()
        Mockito.`when`(client.update("")).thenReturn(e.toString())
        Assertions.assertEquals(e.toString(),controller.update(""))
    }

    @Test
    @DisplayName("checking findValid")
    fun findValid()
    {
        Mockito.`when`(client.findById("")).thenReturn("")
        Assertions.assertEquals("",controller.findById(""))
    }

    @Test
    @DisplayName("checking findInvalid")
    fun findInvalid()
    {
        val e = SQLException()
        Mockito.`when`(client.findById("")).thenReturn(e.toString())
        Assertions.assertEquals(e.toString(),controller.findById(""))
    }

}
