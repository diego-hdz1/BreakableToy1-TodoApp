package com.encora.ToDosBackend.repo;

import com.encora.ToDosBackend.model.ToDo;
import com.encora.ToDosBackend.service.ValidationException;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class ToDoRepo implements ToDoRepoInterface{

    List<ToDo> toDos = new ArrayList<>(Arrays.asList(
            new ToDo(1L, "First task of the week", null, false, LocalDateTime.of(2025,1,31,22,10), 1, LocalDateTime.of(2025,1,10, 19, 20)),
            new ToDo(2L, "Second task of the week", LocalDate.of(2025,1,31), false, LocalDateTime.of(2025,1,31,22,10), 2, LocalDateTime.of(2025,1,11, 11,35)),
            new ToDo(3L, "Third task of the week", LocalDate.of(2025,2,1), false, LocalDateTime.of(2025,1,31,22,10), 3, LocalDateTime.of(2025,1,12,17, 11)),
            new ToDo(4L, "Fourth task of the week", LocalDate.of(2025,2,4), true, null, 2, LocalDateTime.of(2025,1,12, 20, 32)),
            new ToDo(5L, "Fifth task of the week", LocalDate.of(2025,2,5), true, null, 1, LocalDateTime.of(2025,1,13, 11, 12)),
            new ToDo(6L, "Sixth task of the week", LocalDate.of(2025,1,26), true, null, 1, LocalDateTime.of(2025,1,11, 15, 41)),
            new ToDo(7L, "Learn React Context", LocalDate.of(2025,2,8), true, null, 3, LocalDateTime.of(2025,1,9, 10, 12)),
            new ToDo(8L, "School homework", LocalDate.of(2025,3,15), true, null, 2, LocalDateTime.of(2025,1,13, 17, 22)),
            new ToDo(9L, "Finish Tech Log", LocalDate.of(2025,2,20), true, null, 1, LocalDateTime.of(2025,1,12, 18, 38)),
            new ToDo(10L, "Learn about Azure", LocalDate.of(2025,2,22), true, null, 2, LocalDateTime.of(2025,1,10, 19, 21)),
            new ToDo(11L, "Draw", LocalDate.of(2025,3,11), true, null, 3, LocalDateTime.of(2025,1,11, 14, 10)),
            new ToDo(12L, "Go to the office", LocalDate.of(2025,3,17), true, null, 2, LocalDateTime.of(2025,1,12, 10, 10)),
            new ToDo(13L, "Finish reading book", LocalDate.of(2025,3,22), true, null, 1, LocalDateTime.of(2025,1,22, 12, 52))
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
        task.setStatus(true);
        task.setDoneDate(LocalDateTime.now());
        return task;
    }

    @Override
    public ToDo undoneToDo(Long id) {
        ToDo task = findToDoById(id);
        task.setStatus(false);
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
        ToDo removed;
        for(int i=0;i<toDos.size();i++){
            if(Objects.equals(toDos.get(i).getId(), id)){
                removed = toDos.remove(i);
                return true;
            }
        }
        throw new ValidationException("To Do ID not found");
    }
}
