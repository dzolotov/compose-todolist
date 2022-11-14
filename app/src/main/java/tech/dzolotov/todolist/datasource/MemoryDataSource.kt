import tech.dzolotov.todolist.repository.Task

interface DataSource {
    fun complete(task: Task)
    fun getTasks(): List<Task>
    fun getTask(id: Int): Task?
    fun addTask(name: String)
    fun updateTask(id: Int, completed: Boolean? = null, name: String? = null)
    fun removeTask(id: Int)
}

class MemoryDataSource : DataSource {
    private var taskStorage = listOf<Task>()

    override fun complete(task: Task) {
        taskStorage = taskStorage.map {
            println(it.id)
            if (it == task) task.copy(completed = true) else task
        }
    }

    override fun getTasks() = taskStorage

    override fun addTask(name: String) {
        val task =
            Task(
                name = name,
                id = (taskStorage.maxByOrNull { it.id }?.id ?: 0) + 1,
                completed = false
            )
        val mutList = taskStorage.toMutableList()
        mutList.add(task)
        taskStorage = mutList
    }

    override fun getTask(id: Int) = taskStorage.firstOrNull { it.id == id }

    override fun updateTask(id: Int, completed: Boolean?, name: String?) {
        taskStorage = taskStorage.map { task ->
            if (task.id == id) task.copy(
                completed = completed ?: task.completed,
                name = name ?: task.name
            ) else task
        }
    }

    override fun removeTask(id: Int) {
        val newTaskList = taskStorage.toMutableList()
        newTaskList.removeAll { it.id == id }
        taskStorage = newTaskList.toList()
    }
}