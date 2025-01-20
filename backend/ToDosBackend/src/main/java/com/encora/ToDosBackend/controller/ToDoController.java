package com.encora.ToDosBackend.controller;

import com.encora.ToDosBackend.model.ToDo;
import com.encora.ToDosBackend.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ToDoController {

    @Autowired
    ToDoService myService;

    @GetMapping("/todos")
    public ResponseEntity<List<ToDo>> getTodos(
            @RequestParam(required = false) String nameFilter,
            @RequestParam(required = false) Integer priorityFilter,
            @RequestParam(required = false) Boolean filterDone,
            @RequestParam(required = false) Integer pagination,
            @RequestParam(required = false) Integer orderPriority
            //@RequestParam(required = false) String orderDate
    ){          //CHECAR LOS CASOS DE "ALL"
                //SE DEBE DE VALIDAR EN EL FRONT LOS CASOS ESPECIALES
        return new ResponseEntity<>(myService.getTodos(nameFilter, priorityFilter, filterDone, pagination, orderPriority), HttpStatus.OK);
    }

    //To get the information when someone edit a To do
    @GetMapping("/todos/{id}")
    public ResponseEntity<ToDo> getTodo(@PathVariable(required = true) Long id){
        return new ResponseEntity<>(myService.getTodo(id), HttpStatus.OK);
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

    @PutMapping("/todos/{id}/done")
    public ResponseEntity<ToDo> doneTask(@PathVariable(required = true)Long id){
        return new ResponseEntity<>(myService.doneToDo(id), HttpStatus.OK);
    }

    @PutMapping("/todos/{id}/undone")
    public ResponseEntity<ToDo> undoneTask(@PathVariable(required = true)Long id){
        return new ResponseEntity<>(myService.undoneToDo(id), HttpStatus.OK);
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<ToDo> deleteToDo(@PathVariable(required = true)Long id){
        return new ResponseEntity<>(myService.deleteToDo(id), HttpStatus.OK);
    }

    //Pensar bien esto, para tomar las stats de aqui. pero debo de crear un nuevo objeto que devolver.
//    @GetMapping("todos/stats")
//    public ResponseEntity<ToDo> deleteToDo(){
//        return new ResponseEntity<>(myService.getStats(), HttpStatus.OK);
//    }

}
