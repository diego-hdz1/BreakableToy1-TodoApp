package com.encora.ToDosBackend.repository;

import com.encora.ToDosBackend.ToDosBackendApplication;
import com.encora.ToDosBackend.model.ToDo;
import com.encora.ToDosBackend.repo.ToDoRepo;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest(classes = ToDosBackendApplication.class)
class ToDosRepoTest {

    @Autowired
    private ToDoRepo toDoRepo;

    @BeforeEach
    void setup(){
        toDoRepo = new ToDoRepo();
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
        Long id = toDoRepo.generateId();
        ToDo foundById = toDoRepo.getTodo(id-1);
        toDoRepo.doneToDo(foundById.getId());
        assertNotNull(foundById.getDoneDate());
    }

    @Test
    public void testGetTodo(){
        Long id = toDoRepo.generateId();
        ToDo foundById = toDoRepo.undoneToDo(id-1);
        assertNotNull(foundById.getId());
    }

    @Test
    public void testUndoneToDo(){
        Long id = toDoRepo.generateId();
        ToDo foundById = toDoRepo.undoneToDo(id-1);
        assertNull(foundById.getDoneDate());
    }

    @Test
    public void TestUpdateToDo(){
        Long id = toDoRepo.generateId();
        ToDo originalToDo = toDoRepo.getTodo(id-1);
        ToDo updatedToDo = originalToDo.deepCopy();
        updatedToDo.setText("Something else");

        updatedToDo = toDoRepo.updateToDo(updatedToDo, updatedToDo.getId());
        assertNotEquals(originalToDo, updatedToDo);
    }

    @Test
    public void testCreateToDo(){
        ToDo todo = new ToDo(100L, "Created todo test", null, true, LocalDateTime.of(2025,1,31,22,10), 1, LocalDateTime.of(2025,1,10, 19, 20));
        ToDo createdTask = toDoRepo.createToDo(todo);

        assertNotNull(createdTask.getId());
        assertEquals("Created todo test", createdTask.getText());
        assertEquals(true, createdTask.isStatus());

    }
}