package com.encora.ToDosBackend.repo;

import com.encora.ToDosBackend.model.ToDo;
import com.encora.ToDosBackend.service.ValidationException;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class ToDoRepository implements ToDoRepositoryInterface {

    List<ToDo> toDos = new ArrayList<>(Arrays.asList(
            new ToDo(1L, "First task of the week", null, false, LocalDateTime.now().plusDays(22), 1, LocalDateTime.now().minusDays(1)),
            new ToDo(2L, "Second task of the week", LocalDate.now().plusDays(4), false, LocalDateTime.now().plusDays(4), 2, LocalDateTime.now().minusDays(2)),
            new ToDo(3L, "Third task of the week", LocalDate.now().plusDays(36), false, LocalDateTime.now().plusDays(30), 3, LocalDateTime.now().minusDays(3)),
            new ToDo(4L, "Fourth task of the week", LocalDate.now().plusDays(10), true, LocalDateTime.now().plusDays(8), 2, LocalDateTime.now().minusDays(4)),
            new ToDo(5L, "Fifth task of the week", LocalDate.now().plusDays(38), true, LocalDateTime.now().plusDays(28), 1, LocalDateTime.now().minusDays(5)),
            new ToDo(6L, "Sixth task of the week", LocalDate.now().plusDays(2), true, LocalDateTime.now().plusDays(1), 1, LocalDateTime.now().minusDays(1)),
            new ToDo(7L, "Learn React Context", LocalDate.now().plusDays(30), true, LocalDateTime.now().plusDays(20), 3, LocalDateTime.now().minusDays(6)),
            new ToDo(8L, "School homework", LocalDate.now().plusDays(14), true, LocalDateTime.now().plusDays(10), 2, LocalDateTime.now().minusDays(3)),
            new ToDo(9L, "Finish Tech Log", LocalDate.now().plusDays(26), true, LocalDateTime.now().plusDays(18), 1, LocalDateTime.now().minusDays(2)),
            new ToDo(10L, "Learn about Azure", LocalDate.now().plusDays(18), true, LocalDateTime.now().plusDays(12), 2, LocalDateTime.now().minusDays(1)),
            new ToDo(11L, "Draw", LocalDate.now().plusDays(7), true, LocalDateTime.now().plusDays(5), 3, LocalDateTime.now().minusDays(3)),
            new ToDo(12L, "Go to the office", LocalDate.now().plusDays(40), true, LocalDateTime.now().plusDays(30), 2, LocalDateTime.now().minusDays(6)),
            new ToDo(13L, "Finish reading book", LocalDate.now().plusDays(33), true, LocalDateTime.now().plusDays(22), 1, LocalDateTime.now().minusDays(7))

    ));

    @Override
    public List<ToDo> getTodos() {
        return toDos;
    }

    @Override
    public ToDo createToDo(ToDo task) {
        task.setId(generateId());
        toDos.add(task);
        return task;
    }

    @Override
    public Long generateId(){
        return toDos.stream()
                .mapToLong(ToDo::getId)
                .max()
                .orElse(0L)+1;
    }

    @Override
    public ToDo updateToDo(ToDo task, Long id) {
        ToDo existing = findToDoById(id);
        existing.setDoneDate(task.getDoneDate());
        existing.setDueDate(task.getDueDate());
        existing.setText(task.getText());
        existing.setPriority(task.getPriority());
        existing.setStatus(task.isStatus());
        return existing;
    }

    @Override
    public ToDo doneToDo(Long id) {
        ToDo task = findToDoById(id);
        task.setStatus(false);
        task.setDoneDate(LocalDateTime.now());
        return task;
    }

    @Override
    public ToDo undoneToDo(Long id) {
        ToDo task = findToDoById(id);
        task.setStatus(true);
        task.setDoneDate(null);
        return task;
    }

    @Override
    public ToDo getTodo(Long id) {
        return findToDoById(id);
    }

    @Override
    public ToDo findToDoById(Long id) {
        return toDos.stream()
                .filter(todo -> todo.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ValidationException("ToDo with ID " + id + " not found"));
    }

    @Override
    public Boolean deleteToDo(Long id) {
        ToDo task = findToDoById(id);
        return toDos.remove(task);
    }
}
