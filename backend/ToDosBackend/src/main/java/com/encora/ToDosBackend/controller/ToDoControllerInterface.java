package com.encora.ToDosBackend.controller;

import com.encora.ToDosBackend.model.ToDo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ToDoControllerInterface {
    @GetMapping("/todos")
    ResponseEntity<List<ToDo>> getTodos(
            @RequestParam(required = false) String nameFilter,
            @RequestParam(required = false) Integer priorityFilter,
            @RequestParam(required = false) String filterDone,
            @RequestParam(required = false) Integer pagination,
            @RequestParam(required = false) Integer orderPriority,
            @RequestParam(required = false) Integer orderDate
    );

    @GetMapping("/todos/{id}")
    ResponseEntity<ToDo> getTodo(@PathVariable(required = true) Long id);

    @PostMapping("/todos")
    ResponseEntity<ToDo> createToDo(@RequestBody(required = true)ToDo task);

    @PutMapping("/todos/{id}")
    ResponseEntity<ToDo> createToDo(@RequestBody(required = true) ToDo task, @PathVariable(required = true)Long id);

    @PutMapping("/todos/{id}/done")
    ResponseEntity<ToDo> doneTask(@PathVariable(required = true)Long id);

    @PutMapping("/todos/{id}/undone")
    ResponseEntity<ToDo> undoneTask(@PathVariable(required = true)Long id);

    @DeleteMapping("/todos/{id}")
    ResponseEntity<Boolean> deleteToDo(@PathVariable(required = true)Long id);
}
