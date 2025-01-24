package com.encora.ToDosBackend.repository;

import com.encora.ToDosBackend.ToDosBackendApplication;
import com.encora.ToDosBackend.model.ToDo;
import com.encora.ToDosBackend.repo.ToDoRepo;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest(classes = ToDosBackendApplication.class)
class ToDosRepoTest {

    @Autowired
    private ToDoRepo toDoRepo;

    @Test
    public void testGenerateId(){

    }

    @Test
    public void testDeleteById(){
        Long id = toDoRepo.generateId();
        ToDo todo = new ToDo(id, "First test", null, false, LocalDateTime.of(2025,1,31,22,10), 1, LocalDateTime.of(2025,1,10, 19, 20));
        ToDo savedToDo = toDoRepo.createToDo(todo);
        assertTrue(toDoRepo.deleteToDo(id));
    }

    @Test
    public void testFindById(){
        Long id = toDoRepo.generateId();
        ToDo todo = new ToDo(id, "First test", null, false, LocalDateTime.of(2025,1,31,22,10), 1, LocalDateTime.of(2025,1,10, 19, 20));
        ToDo savedToDo = toDoRepo.createToDo(todo);
        assertEquals("First test", toDoRepo.getTodo(id).getText());
    }

    @Test
    public void testDoneToDo(){

    }

    @Test
    public void testUndoneToDo(){

    }

    @Test
    public void TestUpdateToDo(){

    }

    @Test
    public void testCreateToDo(){

    }



}
