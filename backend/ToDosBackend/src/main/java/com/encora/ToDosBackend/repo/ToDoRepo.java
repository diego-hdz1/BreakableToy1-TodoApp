package com.encora.ToDosBackend.repo;

import com.encora.ToDosBackend.model.ToDo;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Repository
public class ToDoRepo implements ToDoRepoInterface{

    List<ToDo> toDos = new ArrayList<>(Arrays.asList(
            new ToDo(1L, "Primera tarea de la semana", null, false, LocalDateTime.of(2025,1,31,22,10), 1, LocalDateTime.of(2025,1,10, 19, 20)),
            new ToDo(2L, "Segunda tarea de la semana", LocalDate.of(2025,1,31), false, LocalDateTime.of(2025,1,31,22,10), 2, LocalDateTime.of(2025,1,11, 11,35)),
            new ToDo(3L, "Tercera tarea de la semana", LocalDate.of(2025,2,1), false, LocalDateTime.of(2025,1,31,22,10), 3, LocalDateTime.of(2025,1,12,17, 11)),
            new ToDo(4L, "Cuarta tarea de la semana", LocalDate.of(2025,2,4), true, null, 2, LocalDateTime.of(2025,1,12, 20, 32)),
            new ToDo(5L, "Quinta tarea de la semana", LocalDate.of(2025,2,5), true, null, 1, LocalDateTime.of(2025,1,13, 11, 12)),
            new ToDo(6L, "Sexta tarea de la semana", LocalDate.of(2025,1,26), true, null, 1, LocalDateTime.of(2025,1,11, 15, 41)),
            new ToDo(7L, "Septima tarea de la semana", LocalDate.of(2025,2,6), true, null, 3, LocalDateTime.of(2025,1,9, 10, 12)),
            new ToDo(8L, "Ocatava tarea de la semana", LocalDate.of(2025,3,15), true, null, 2, LocalDateTime.of(2025,1,13, 17, 22)),
            new ToDo(9L, "Novena tarea de la semana", LocalDate.of(2025,2,20), true, null, 1, LocalDateTime.of(2025,1,12, 18, 38)),
            new ToDo(10L, "Decima tarea de la semana", LocalDate.of(2025,2,22), true, null, 2, LocalDateTime.of(2025,1,10, 19, 21)),
            new ToDo(11L, "Onceava tarea de la semana", LocalDate.of(2025,3,11), true, null, 3, LocalDateTime.of(2025,1,11, 14, 10)),
            new ToDo(12L, "Doceava tarea de la semana", LocalDate.of(2025,3,17), true, null, 2, LocalDateTime.of(2025,1,12, 10, 10)),
            new ToDo(13L, "Acabar tarea de la semana", LocalDate.of(2025,3,22), true, null, 1, LocalDateTime.of(2025,1,22, 12, 52))
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
        for(ToDo temp : toDos){
            if(temp.getId() == id){
                temp.setDoneDate(task.getDoneDate());
                temp.setDueDate(task.getDueDate());
                temp.setText(task.getText());
                temp.setPriority(task.getPriority());
                temp.setStatus(task.isStatus());
                break;
            }
        }
        return task;
    }

    @Override
    public ToDo doneToDo(Long id) {
        for(ToDo temp : toDos){
            if(temp.getId().equals(id)){
                temp.setStatus(!temp.isStatus());
                temp.setDoneDate(LocalDateTime.now());
                return temp;
            }
        }
        return null;
    }

    @Override
    public ToDo undoneToDo(Long id) {
        for(ToDo temp : toDos){
            if(temp.getId() == id){
                temp.setStatus(!temp.isStatus());
                temp.setDoneDate(null);
                return temp;
            }
        }
        return null;
    }

    @Override
    public ToDo getTodo(Long id) {
        for(ToDo temp : toDos){
            if(temp.getId() == id){
                return temp;
            }
        }
        return null;
    }

    @Override
    public Boolean deleteToDo(Long id) {
        ToDo removed;
        for(int i=0;i<toDos.size();i++){
            if(toDos.get(i).getId() == id){
                removed = toDos.remove(i);
                return true;
            }
        }
        return false;
    }
}
