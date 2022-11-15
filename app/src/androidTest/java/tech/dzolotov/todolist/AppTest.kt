package tech.dzolotov.todolist

import DataSource
import android.app.Application
import android.content.Context
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.runner.AndroidJUnitRunner
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.junit.Rule
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.koin.test.KoinTestRule
import tech.dzolotov.todolist.repository.ITodoRepository
import tech.dzolotov.todolist.repository.Task
import tech.dzolotov.todolist.ui.theme.TodolistTheme

class TestDataSource : DataSource {
    override fun complete(task: Task) {
        TODO("Not yet implemented")
    }

    override fun getTasks(): List<Task> {
        TODO("Not yet implemented")
    }

    override fun getTask(id: Int): Task? {
        TODO("Not yet implemented")
    }

    override fun addTask(name: String) {
        TODO("Not yet implemented")
    }

    override fun updateTask(id: Int, completed: Boolean?, name: String?) {
        TODO("Not yet implemented")
    }

    override fun removeTask(id: Int) {
        TODO("Not yet implemented")
    }

}

class TestRepository(val dataSource: DataSource) : ITodoRepository {
    val state = MutableStateFlow<List<Task>>(listOf())

    override fun getState(): StateFlow<List<Task>> = state

    override fun add(name: String) {
        TODO("Not yet implemented")
    }

    override fun remove(id: Int) {
        TODO("Not yet implemented")
    }

    override fun complete(id: Int) {
        TODO("Not yet implemented")
    }

    override fun uncomplete(id: Int) {
        TODO("Not yet implemented")
    }

    override fun getTasks(): List<Task> = emptyList()

    override suspend fun load() {
    }
}

val testModule = module {
    viewModel { MainViewModel() }
    single<DataSource> { TestDataSource() }
    single<ITodoRepository> { TestRepository(get()) }
}

class CustomTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, CustomApplication::class.java.name, context)
    }
}

class CustomApplication : Application()

class ToDoListTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val koinTest = KoinTestRule.create {
        modules(testModule)
    }

    @Test
    fun myTest() {
        // Start the app
        composeTestRule.apply {
            setContent {
                TodolistTheme {
                    TodoListApp()
                }
            }
            onNodeWithTag("header").assert(hasText("Todo List"))
        }
    }
}
