package com.encora.ToDosBackend.service;

import com.encora.ToDosBackend.model.ToDo;

import java.util.List;

public interface ToDoService {

    List<ToDo> getTodos(String nameFilter, Integer priorityFilter, String filterDone, Integer pagination, Integer orderPriority, Integer orderDate);
    List<ToDo> filterTodos(List<ToDo> todos, String nameFilter, Integer priorityFilter, String filterDone);
    List<ToDo> paginateTodos(List<ToDo> todos, Integer pagination, Integer pageSize);
    List<ToDo> orderTodos(List<ToDo> todos, Integer orderPriority , Integer orderDate);
    ToDo createToDo(ToDo task);
    ToDo updateToDo(ToDo task, Long id);
    ToDo undoneToDo(Long id);
    ToDo getTodo(Long id);
    Boolean deleteToDo(Long id);
    ToDo doneToDo(Long id);
}
