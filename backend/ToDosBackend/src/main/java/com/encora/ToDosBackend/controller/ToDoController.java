package com.encora.ToDosBackend.controller;

import com.encora.ToDosBackend.model.ToDo;
import com.encora.ToDosBackend.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ToDoController {

    @Autowired
    ToDoService myService;

    @GetMapping("/todos")
    public ResponseEntity<List<ToDo>> getTodos(){
        return new ResponseEntity<>(myService.getTodos(), HttpStatus.OK);
        ///Include pagination. Pages should be of 10 elements.
        /// Filter by done/undone
        /// Filter by the name or part of the name
        /// Filter by priority ///
    }

    @PostMapping("/todos")
    public ResponseEntity<ToDo> createToDo(@RequestBody(required = true)ToDo task){
        return new ResponseEntity<>(myService.createToDo(task), HttpStatus.CREATED);
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<ToDo> createToDo(@RequestBody(required = true) ToDo task, @PathVariable(required = true)Long id){
        return new ResponseEntity<>(myService.updateToDo(task, id), HttpStatus.OK);

        //Checar que no regrese nada en el <> porque no es necesario
    }

    //POST ?????
//    @PostMapping("/todos/{id}/done")
//    public ResponseEntity<ToDo> doneTask()

    @PutMapping("/todos/{id}/undone")
    public ResponseEntity<ToDo> undoneTask(@PathVariable(required = true)Long id){
        return new ResponseEntity<>(myService.undoneToDo(id), HttpStatus.OK);
    }

}
