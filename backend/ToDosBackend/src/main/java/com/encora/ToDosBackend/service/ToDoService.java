package com.encora.ToDosBackend.service;

import com.encora.ToDosBackend.model.ToDo;
import com.encora.ToDosBackend.repo.ToDoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoService {

    @Autowired
    ToDoRepo myRepo;

    public List<ToDo> getTodos() {
        return myRepo.getTodos();
    }

    public ToDo createToDo(ToDo task) {
        //Validation !!!
        return myRepo.createToDo(task);
    }

    public ToDo updateToDo(ToDo task, Long id) {
        return myRepo.updateToDo(task, id);
    }

    public ToDo undoneToDo(Long id) {
        return myRepo.undoneToDo(id);
    }

    public ToDo getTodo(Long id) {
        return myRepo.getTodo(id);
    }
}
