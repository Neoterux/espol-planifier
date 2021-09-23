package com.neoterux.espfier.data.factory

import com.neoterux.espfier.data.Course
import com.neoterux.espfier.data.Subject
import javafx.scene.control.*
import javafx.scene.layout.Background
import javafx.scene.layout.Pane
import javafx.util.Callback


class DataFactory(val onClick: (Course) -> Unit, val converter: (Course)->ToggleGroup?) : Callback<TreeView<Any>, TreeCell<Any>> {

//    private val relation = mutableMapOf<Course, Subject>()
//
//    var Course.subject: Subject?
//        get() = relation[this@subject]
//        set(value) {
//                relation[this] = value!!
//        }

    private val alreadyAdded = mutableListOf<Any>()

    override fun call(param: TreeView<Any>): TreeCell<Any> = object : TreeCell<Any>() {

        override fun updateItem(item: Any?, empty: Boolean) {
            super.updateItem(item, empty)
            if (item == null || empty){
                text = null
                graphic = null
                return
            }
            when(item) {
                is Subject -> subjectItem(item)
                is Course -> courseItem(item)
                is String -> graphic = Label(item)
                null -> println("Null not expected:  $item, $empty")
                else -> throw IllegalStateException("Inserted data is invalid $item")
            }

        }

        fun subjectItem(subject: Subject) {
            text = subject.name
            graphic = Pane().apply {
                style = "-fx-background-color: ${subject.color.hex()};" +
                        "-fx-border-color: black;" +
                        "-fx-border-width: 2px;"
            children.add(Label("   "))
            }
        }

        fun courseItem(course: Course) {
            graphic = RadioButton("Paralelo: ${course.id}").also {
                it.toggleGroup = converter(course)
                it.setOnAction { onClick(course) }
            }
        }
    }

    fun clear() = alreadyAdded.clear()
}
