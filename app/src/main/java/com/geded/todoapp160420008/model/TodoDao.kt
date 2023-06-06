package com.geded.todoapp160420008.model

import androidx.room.*

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg todo: Todo)

    @Query("SELECT * from todo WHERE is_done=0 ORDER BY priority DESC")
    fun selectAllTodo(): List<Todo>

    @Query("SELECT * from todo WHERE uuid=:id")
    fun selectTodo(id:Int): Todo

    @Query("UPDATE todo SET title=:title, notes=:notes, priority=:priority where uuid=:id")
    fun update(title:String, notes:String, priority:Int, id:Int)

    @Query("UPDATE todo SET is_done=1 WHERE uuid=:id")
    fun doneTodo(id:Int)

    @Delete
    fun deleteTodo(todo:Todo)
}