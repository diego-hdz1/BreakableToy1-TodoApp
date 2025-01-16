package com.encora.ToDosBackend.repo;

import com.encora.ToDosBackend.model.ToDo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class ToDoRepo {

    List<ToDo> toDos = new ArrayList<>(Arrays.asList(
       new ToDo(1L, "Primera tarea de la semana", "20/1/2025", false, "", 1),
            new ToDo(2L, "Segunda tarea de la semana", "29/1/2025", false, "", 2),
            new ToDo(3L, "Tercera tarea de la semana", "30/1/2025", false, "", 3),
            new ToDo(4L, "Cuarta tarea de la semana", "2/2/2025", false, "", 2)
    ));

    public List<ToDo> getTodos() {
        return toDos;
    }

    public ToDo createToDo(ToDo task) {
        toDos.add(task);
        return task;
    }

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

    public ToDo undoneToDo(Long id) {
        for(ToDo temp : toDos){
            if(temp.getId() == id){
                temp.setStatus(!temp.isStatus());
                temp.setDoneDate("");
                break;
            }
        }
        ToDo temp = new ToDo();
        return temp;
    }
}
