package com.encora.ToDosBackend.controller;

import com.encora.ToDosBackend.model.ToDo;
import com.encora.ToDosBackend.service.ToDoServiceImpl;
import com.encora.ToDosBackend.service.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class ToDoController implements ToDoApi {

    @Autowired
    ToDoServiceImpl toDoService;

    @Override
    public ResponseEntity<List<ToDo>> getTodos(
            @RequestParam(required = false) String nameFilter,
            @RequestParam(required = false) Integer priorityFilter,
            @RequestParam(required = false) String filterDone,
            @RequestParam(required = false) Integer pagination,
            @RequestParam(required = false) Integer orderPriority,
            @RequestParam(required = false) Integer orderDate
    ){
        return new ResponseEntity<>(toDoService.getTodos(nameFilter, priorityFilter, filterDone, pagination, orderPriority, orderDate), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ToDo> getTodo(@PathVariable(required = true) Long id){
        return new ResponseEntity<>(toDoService.getTodo(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ToDo> createToDo(@RequestBody(required = true)ToDo task){
        ToDo response = toDoService.createToDo(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<ToDo> updateToDo(@RequestBody(required = true) ToDo task, @PathVariable(required = true)Long id){
        return new ResponseEntity<>(toDoService.updateToDo(task, id), HttpStatus.OK);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleException(ValidationException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @Override
    public ResponseEntity<ToDo> doneTask(@PathVariable(required = true)Long id){
        return new ResponseEntity<>(toDoService.doneToDo(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ToDo> undoneTask(@PathVariable(required = true)Long id){
        return new ResponseEntity<>(toDoService.undoneToDo(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Boolean> deleteToDo(@PathVariable(required = true)Long id){
        return new ResponseEntity<>(toDoService.deleteToDo(id), HttpStatus.OK);
    }
}
