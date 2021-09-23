package com.neoterux.espfier.view.controllers

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParseException
import com.neoterux.espfier.custom.ColorRepository
import com.neoterux.espfier.custom.ScheduleTable
import com.neoterux.espfier.data.Course
import com.neoterux.espfier.data.Schedule
import com.neoterux.espfier.data.Subject
import com.neoterux.espfier.data.factory.DataFactory
import com.neoterux.espfier.utils.filterFiles
import com.neoterux.espfier.utils.filteredFiles
import com.neoterux.espfier.utils.openDir
import com.neoterux.espfier.utils.ui
import javafx.application.Platform
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.geometry.Pos
import javafx.scene.Group
import javafx.scene.control.*
import javafx.scene.layout.HBox
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow


class StartupController {
    @FXML
    private lateinit var openItem: MenuItem

    @FXML
    private lateinit var saveItem: MenuItem

    @FXML
    private lateinit var createItem: MenuItem

    @FXML
    private lateinit var closeItem: MenuItem

    @FXML
    private lateinit var aboutItem: MenuItem

    @FXML
    private lateinit var boxLoading: HBox

    @FXML
    private lateinit var normalContainer: TitledPane

    @FXML
    private lateinit var examContainer: TitledPane

    @FXML
    private lateinit var trvData: TreeView<Any>

    private var loadedData: Flow<Subject> = loadFiles()

    private var normalTable = ScheduleTable()

    private var examTable = ScheduleTable()

    private var dataRootContainer: TreeItem<Any> = TreeItem("")

    private val instColor = ColorRepository.copyColors()

    private val schedOwner = mutableMapOf<Course, Subject>()

    private val groups = mutableMapOf<Subject, ToggleGroup>()

    private val converter: (Course)->ToggleGroup? = { groups[schedOwner[it]] }

    private var visibleSubjects: MutableList<Subject> = arrayListOf()

    @FXML
    fun initialize() {
        normalContainer.content = wrapTable(normalTable)
        examContainer.content = wrapTable(examTable)
        trvData.cellFactory = DataFactory(::onRadioClick, converter)
        trvData.isShowRoot = false
        trvData.root = dataRootContainer
        GlobalScope.launch (Dispatchers.IO){
            configureTreeView()
        }
    }

    fun onRadioClick(item: Course) {
        val sub = schedOwner[item] ?: return
        if (sub in visibleSubjects){
            replaceSched(item, sub)
        }else{
            putSched(item, sub)
        }
    }

    private fun wrapTable(table: ScheduleTable) = HBox(table).apply {
        alignment = Pos.TOP_CENTER
        isFillHeight = true
        isCache = true
    }


    fun replaceSched(item:Course, subject: Subject){
        normalTable.cleanFrom(subject)
        putSched(item, subject)
    }

    fun putSched(item: Course, subject: Subject){
        normalTable.addScheds(item.dailySched, subject, item)
        examTable.addScheds(listOf(item.examSched), subject, item)
        if (subject !in visibleSubjects)
            visibleSubjects.add(subject)
    }

    fun exitWindow(event: ActionEvent) {
        Platform.exit()
    }

    fun loadFiles() = flow<Subject> {
        ui{ boxLoading.isVisible = true }
        val  gson = Gson()
        openDir("data")
                .filteredFiles { it.extension == "json" }
                .apply { if(isEmpty())  println("Nothing after filter") }
                .forEach {
                    try{
                        val data = gson.fromJson(it.readText(), Subject::class.java).also {sbj ->
                            sbj.color = instColor.pop()
                        }
                        emit(data)
                    }catch (e: JsonParseException){
                        println("[ERROR] invalid json parse")
                    }
                }
        ui { boxLoading.isVisible = false }
    }

    private suspend fun configureTreeView(){
        loadedData.collect {
            val item: TreeItem<Any> = TreeItem(it)
            val group = ToggleGroup()
            it.schedules.forEach {course ->
                TreeItem<Any>(course).also { titem ->
                    item.children.add(titem)
                    schedOwner[course] = it

                }
            }
            dataRootContainer.children.add(item)
            groups[it] = group
        }
    }

}
