package com.neoterux.espfier.utils

import com.neoterux.espfier.MainApp
import com.neoterux.espfier.data.Hour
import javafx.application.Platform
import javafx.fxml.FXMLLoader
import javafx.geometry.Insets
import javafx.scene.Parent
import javafx.scene.layout.ColumnConstraints
import javafx.scene.layout.RowConstraints
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.javafx.JavaFx
import kotlinx.coroutines.launch
import java.net.URL
import kotlin.coroutines.CoroutineContext

/**
 * get a resource with a relative location from the class its called.
 * @param location the relative location
 * @return an Url of the especified resource, may be null if location is invalid
 */
inline fun <reified T: Any> T.res(location: String): URL? = T::class.java.getResource(location)


inline fun <reified T : Any> T.loaderOf(location: String): Parent  = FXMLLoader.load(this.res(location)!!)


fun rowConstraint(hpercent:Double, action: (RowConstraints.()->Unit)? = null): RowConstraints =
    RowConstraints().apply {
        action?.invoke(this)
        percentHeight = hpercent * 100
    }

fun columConstraint(wpercent:Double, action: (ColumnConstraints.()->Unit)? = null) =
    ColumnConstraints().apply {
        action?.invoke(this)
        percentWidth = wpercent * 100
    }


fun insets(top:Double = 0.0, right:Double = 0.0, bottom:Double = 0.0, left:Double = 0.0) = Insets(top, right, bottom, left)

fun ui(action: ()->Unit) = Platform.runLater(action)

fun onUi(job: CoroutineScope.() -> Unit) = GlobalScope.launch(Dispatchers.JavaFx){ job() }

