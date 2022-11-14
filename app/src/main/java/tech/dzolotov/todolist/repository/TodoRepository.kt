package tech.dzolotov.todolist.repository

import DataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class Task(var id:Int, var name: String, var completed: Boolean)

interface ITodoRepository {

    fun getState(): StateFlow<List<Task>>

    fun add(name:String)
    fun remove(id:Int)
    fun complete(id:Int)
    fun uncomplete(id:Int)
    fun getTasks():List<Task>
}

class TodoRepository(val dataSource: DataSource) : ITodoRepository {

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
}