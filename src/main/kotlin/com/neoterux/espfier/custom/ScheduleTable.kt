package com.neoterux.espfier.custom

import com.neoterux.espfier.custom.Color.Companion.contrast
import com.neoterux.espfier.data.Course
import com.neoterux.espfier.data.Hour
import com.neoterux.espfier.data.Schedule
import com.neoterux.espfier.data.Subject
import com.neoterux.espfier.utils.*
import javafx.geometry.HPos
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.control.Tooltip
import javafx.scene.layout.GridPane
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox

class ScheduleTable: GridPane() {

    companion object {
        private val days = listOf("[SAMPLE]", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes")

        private val universalInit = Hour(7, 0)
    }

    private val insertedScheds = mutableMapOf<Subject, List<Schedule>>()

    init {
        for (i in 1..26){
            rowConstraints += rowConstraint(-1.0).apply { minHeight = 24.0 }
            if(i < 7)
                columnConstraints += columConstraint(0.15){
                    hgrow = if(i>1) Priority.ALWAYS else Priority.NEVER
                    minWidth = 75.0
                    this.halignment = HPos.CENTER
                    add(Label(days[i-1]), i-1, 0)
                }
        }
        fixUi()
        fillHeaders()
    }

    private fun fixUi(){
        setPrefSize(650.0, 496.0)
        gridLinesVisibleProperty().set(true)
        columnConstraints.first().apply {
            percentWidth = -1.0
        }
    }

    private fun fillHeaders(){
        val time = Hour(7, 0)
        for (i in 1..26){
            var strRange = time.toString()
            time.addMinutes(30)
            strRange = "$strRange - $time"
            add(Label(strRange).apply {
                padding = insets(right = 10.0, left = 10.0)
            }, 0, i)
        }
    }

    private operator fun get(row: Int, col: Int) : Node{
        println("Rowcount: ${this.rowCount}, colcount: $columnCount")
        return this.children[columnCount * row + col]
    }


    fun delete(row: Int, col: Int) = children.remove(this[row, col])

    fun addCourse(course: Course){

    }

    fun addScheds(schedules: List<Schedule>, sub: Subject, owner: Course) = schedules.forEach{ schedule ->
        println("Registering $schedule")
        val row = schedule.initTime.index()
        val col = days.indexOf(schedule.day)
        println("Target pos: <$row, $col>")
        add(SchedContainer(sub, owner), col, row, 1,  schedule.cellQuantity())
        insertedScheds[sub] = schedules
    }

    private fun Hour.index() = (this - universalInit).totalMinutes() / 30 + 1

    /**
     * Clean all schedules from a specific Subject
     */
    fun cleanFrom(sub: Subject) {
        val target = children.dropWhile { it !is SchedContainer }.map { it as SchedContainer }
        for (node in target) {
            if (node.sub === sub)
                children.remove(node)
        }
    }
//        insertedScheds[sub]?.forEach {schedule ->
//        val row = schedule.initTime.index()
//        val col = days.indexOf(schedule.day)
//        if(delete(row, col))
//            println("removed previous data from ${sub.name}")
//        else
//            println("Cannot remove data from ${sub.name}")
//    } ?: Unit


    private class SchedContainer(val sub: Subject, course: Course) : ScrollPane(){

        init {
            this.isFitToHeight = true
            isFitToWidth = true
            content = VBox().apply {
                style = "-fx-background-color: ${sub.color.hex()};"
                tooltip = Tooltip(course.teacherName)
                isFitToWidth = true
                this.spacing = 3.0
                this.alignment = Pos.TOP_CENTER
                this.maxHeight = Double.MAX_VALUE
                children.addAll(listOf(
                        legibleLabel(sub.name),
                        legibleLabel(course.classNo),
                        legibleLabel("paralelo: ${course.id}")
                ))
            }
        }

        private fun legibleLabel(text: String) = Label(text).also {
            it.style = "-fx-text-fill: ${sub.color.contrast().hex()}"
            it.isWrapText = true
            setFillHeight(it, true)
        }
    }

}
