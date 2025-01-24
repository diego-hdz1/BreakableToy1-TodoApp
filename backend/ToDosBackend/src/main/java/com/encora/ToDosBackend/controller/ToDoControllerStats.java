package com.encora.ToDosBackend.controller;

import com.encora.ToDosBackend.model.ToDoStats;
import com.encora.ToDosBackend.service.ToDoStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class ToDoControllerStats {

    @Autowired
    ToDoStatsService toDoStatsService;

    @GetMapping("/todos/stats")
    public ResponseEntity<ToDoStats> getStats(){
        return new ResponseEntity<>(toDoStatsService.getStats(), HttpStatus.OK);
    }
}
