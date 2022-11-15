package tech.dzolotov.todolist

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import org.koin.java.KoinJavaComponent.inject
import tech.dzolotov.todolist.repository.ITodoRepository

class MainViewModel : ViewModel() {

    val repository: ITodoRepository by inject(ITodoRepository::class.java)

    suspend fun loadData() {
        delay(1000L)
        repository.load()
    }
}