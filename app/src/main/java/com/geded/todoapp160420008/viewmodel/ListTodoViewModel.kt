package com.geded.todoapp160420008.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.geded.todoapp160420008.model.Todo
import com.geded.todoapp160420008.model.TodoDatabase
import com.geded.todoapp160420008.util.buildDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ListTodoViewModel(application: Application):AndroidViewModel(application), CoroutineScope {
    val todoLD = MutableLiveData<List<Todo>>()
    val todoLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    private var job = Job()

    override val coroutineContext:CoroutineContext
    get() = job + Dispatchers.IO

    fun refresh(){
        loadingLD.value = true
        todoLoadErrorLD.value = false
        launch{
            val db = buildDB(getApplication())

            todoLD.postValue(db.todoDao().selectAllTodo())
        }
    }

    fun clearTask(todo: Todo){
        launch {
            val db = Room.databaseBuilder(getApplication(), TodoDatabase::class.java, "newtododb").build()
            db.todoDao().doneTodo(todo.uuid)

            todoLD.postValue(db.todoDao().selectAllTodo())
        }
    }
}