package com.encora.ToDosBackend.repo;

import com.encora.ToDosBackend.model.ToDo;

import java.util.List;

public interface ToDoRepoInterface {
    List<ToDo> getTodos();
    ToDo createToDo(ToDo task);
    Long generateId();
    ToDo updateToDo(ToDo task, Long id);
    ToDo doneToDo(Long id);
    ToDo undoneToDo(Long id);
    ToDo getTodo(Long id);
    Boolean deleteToDo(Long id);

}
