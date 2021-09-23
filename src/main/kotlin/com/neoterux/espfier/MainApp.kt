package com.neoterux.espfier

import com.neoterux.espfier.utils.loaderOf
import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage
import java.net.URI
import java.nio.file.*


class MainApp: Application() {

    companion object {
        lateinit var mainWindow: Stage
            private set
    }

    /**
     * Init application method before gui.
     */
    override fun init() {
        super.init()
        Paths.get("data").toFile().takeIf { !it.exists() }?.mkdir()
    }

    override fun start(primaryStage: Stage) {
        val fs: FileSystem = FileSystems.getFileSystem(URI.create("jrt:/"))
        val objClassFilePath: Path = fs.getPath("modules", "java.base", "java/lang/Object.class")
        println(objClassFilePath)
        val root = loaderOf("view/startup_view.fxml")
        mainWindow = primaryStage
        with(primaryStage) {
            scene = Scene(root, 680.0, 450.0)
            minWidth = 725.0
            minHeight = 550.0
            show()
        }

    }
}
