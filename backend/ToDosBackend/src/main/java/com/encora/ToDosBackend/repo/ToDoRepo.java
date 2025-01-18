package com.encora.ToDosBackend.repo;

import com.encora.ToDosBackend.model.ToDo;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
        //Create a unique ID, going to do it by checking maximum id and add it 1
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

    public ToDo doneToDo(Long id) {
        for(ToDo temp : toDos){
            if(temp.getId() == id){
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                System.out.println("Current Date and Time: " + formatter.format(date));
                temp.setStatus(!temp.isStatus());
                temp.setDoneDate(formatter.format(date));
                break;
            }
        }
        ToDo temp = new ToDo(); //I will eliminate this in the future, to not return an object
        return temp;
    }

    public ToDo undoneToDo(Long id) {
        for(ToDo temp : toDos){
            if(temp.getId() == id){
                temp.setStatus(!temp.isStatus());
                temp.setDoneDate("");
                break;
            }
        }
        ToDo temp = new ToDo(); //I will eliminate this in the future, to not return an object
        return temp;
    }

    public ToDo getTodo(Long id) {
        for(ToDo temp : toDos){
            if(temp.getId() == id){
                return temp;
            }
        }
        ToDo temp = new ToDo(); //I will eliminate this in the future, to not return an object
        return temp;
    }

    public ToDo deleteToDo(Long id) {
        ToDo removed;
        for(int i=0;i<toDos.size();i++){
            if(toDos.get(i).getId() == id){
                removed = toDos.remove(i);
            }
        }
        ToDo temp = new ToDo(); //I will eliminate this in the future, to not return an object
        return temp;
    }
}
