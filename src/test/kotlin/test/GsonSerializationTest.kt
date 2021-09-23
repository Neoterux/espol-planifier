package test

import com.neoterux.espfier.utils.listFiles
import com.neoterux.espfier.utils.openDir
import com.neoterux.espfier.view.controllers.StartupController
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.mockito.kotlin.mock
import java.io.FileWriter
import java.lang.RuntimeException
import java.nio.file.Files
import java.nio.file.Paths

class GsonSerializationTest {

    private var testController: StartupController = StartupController()

    companion object {

        @BeforeAll
        @JvmStatic
        fun copytestFile() {
            println("Is executing this?")
            openDir("src/test/resources/sample_data.json")
                    .apply {
                        println("is opened? ${this.exists()}")
                    }
                    .copyTo(openDir("data", "sample_data.json"), true, 1000)
                    .apply {
                        println("Successfull copy file at ${this.absolutePath}")
                    }
        }
    }

    @Test
    fun testListFiles() {
        println("This should list files in data directory")
        listFiles("data").forEach { println(it) }
    }

    @Test
    fun testLoad(){
        runBlocking {
            testController.loadFiles().collect {
                println(it)
            }
        }
    }
}
