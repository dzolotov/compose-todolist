package tech.dzolotov.todolist.di

import DataSource
import MemoryDataSource
import androidx.lifecycle.viewmodel.compose.viewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import tech.dzolotov.todolist.MainViewModel
import tech.dzolotov.todolist.repository.ITodoRepository
import tech.dzolotov.todolist.repository.TodoRepository

val appModule = module {
    viewModel { MainViewModel() }
    single<DataSource> { MemoryDataSource() }
    single<ITodoRepository> { TodoRepository(get()) }
}