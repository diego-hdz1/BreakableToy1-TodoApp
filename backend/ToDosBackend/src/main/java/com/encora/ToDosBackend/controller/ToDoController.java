package com.encora.ToDosBackend.controller;

import com.encora.ToDosBackend.model.ToDo;
import com.encora.ToDosBackend.model.ToDoStats;
import com.encora.ToDosBackend.service.ToDoService;
import com.encora.ToDosBackend.service.ToDoStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class ToDoController {

    @Autowired
    ToDoService toDoService;

    @GetMapping("/todos")
    public ResponseEntity<List<ToDo>> getTodos(
            @RequestParam(required = false) String nameFilter,
            @RequestParam(required = false) Integer priorityFilter,
            @RequestParam(required = false) String filterDone,
            @RequestParam(required = false) Integer pagination,
            @RequestParam(required = false) Integer orderPriority,
            @RequestParam(required = false) Integer orderDate
    ){
                //SE DEBE DE VALIDAR EN EL FRONT LOS CASOS ESPECIALES
        return new ResponseEntity<>(toDoService.getTodos(nameFilter, priorityFilter, filterDone, pagination, orderPriority, orderDate), HttpStatus.OK);
    }

    //To get the information when someone edit a To do
    @GetMapping("/todos/{id}")
    public ResponseEntity<ToDo> getTodo(@PathVariable(required = true) Long id){
        return new ResponseEntity<>(toDoService.getTodo(id), HttpStatus.OK);
    }

    @PostMapping("/todos")
    public ResponseEntity<ToDo> createToDo(@RequestBody(required = true)ToDo task){
        return new ResponseEntity<>(toDoService.createToDo(task), HttpStatus.CREATED);
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<ToDo> createToDo(@RequestBody(required = true) ToDo task, @PathVariable(required = true)Long id){
        //LocalDateTime formatter = task.setDueDate(LocalDate.parse(task.getDoneDate()).atTime());
        return new ResponseEntity<>(toDoService.updateToDo(task, id), HttpStatus.OK);

        //Checar que no regrese nada en el <> porque no es necesario
    }

    @PutMapping("/todos/{id}/done")
    public ResponseEntity<ToDo> doneTask(@PathVariable(required = true)Long id){
        return new ResponseEntity<>(toDoService.doneToDo(id), HttpStatus.OK);
    }

    @PutMapping("/todos/{id}/undone")
    public ResponseEntity<ToDo> undoneTask(@PathVariable(required = true)Long id){
        return new ResponseEntity<>(toDoService.undoneToDo(id), HttpStatus.OK);
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Boolean> deleteToDo(@PathVariable(required = true)Long id){
        return new ResponseEntity<>(toDoService.deleteToDo(id), HttpStatus.OK);
    }
}
