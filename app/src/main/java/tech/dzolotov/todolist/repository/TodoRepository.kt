package tech.dzolotov.todolist.repository

import DataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent

data class Task(var id:Int, var name: String, var completed: Boolean)

interface ITodoRepository {

    fun getState(): StateFlow<List<Task>>

    fun add(name:String)
    fun remove(id:Int)
    fun complete(id:Int)
    fun uncomplete(id:Int)
    fun getTasks():List<Task>
    suspend fun load()
}

class TodoRepository(val dataSource: DataSource) : ITodoRepository, KoinComponent {

    private val stateFlow = MutableStateFlow<List<Task>>(listOf())

    override fun getState(): StateFlow<List<Task>> {
        return stateFlow
    }

    override fun add(name: String) {
        dataSource.addTask(name)
        stateFlow.value = dataSource.getTasks()
    }

    override fun remove(id: Int) {
        dataSource.removeTask(id)
        stateFlow.value = dataSource.getTasks()
    }

    override fun complete(id: Int) {
        dataSource.updateTask(id, completed = true)
        stateFlow.value = dataSource.getTasks()
    }

    override fun uncomplete(id: Int) {
        dataSource.updateTask(id, completed = false)
        stateFlow.value = dataSource.getTasks()
    }

    override fun getTasks() = dataSource.getTasks()
    override suspend fun load() {
        //initialize list
        dataSource.addTask("Sample task 1")
        dataSource.addTask("Sample task 2")
        dataSource.addTask("Sample task 3")
    }
}