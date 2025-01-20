package com.encora.ToDosBackend.service;

import com.encora.ToDosBackend.model.ToDo;
import com.encora.ToDosBackend.repo.ToDoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ToDoService {

    @Autowired
    ToDoRepo myRepo;

    public List<ToDo> getTodos(String nameFilter, Integer priorityFilter, Boolean filterDone, Integer pagination, Integer orderPriority) {
        List<ToDo> todos =myRepo.getTodos();
//        System.out.println(nameFilter);
//        System.out.println(priorityFilter);
//        System.out.println(filterDone);
//        System.out.println(pagination);
//        System.out.println(orderPriority);
        todos = filterTodos(todos, nameFilter, priorityFilter, filterDone);
        todos = orderTodos(todos, orderPriority);
        todos = paginateTodos(todos, pagination, 10);
        return todos;
    }

    private List<ToDo> filterTodos(List<ToDo> todos, String nameFilter, Integer priorityFilter, Boolean filterDone) {
        if(nameFilter == null && priorityFilter == null && filterDone == null) return todos;
//        System.out.println(todos.get(0).toString());
//        System.out.println(nameFilter);
//        System.out.println(priorityFilter);
//        System.out.println(filterDone);
         List<ToDo> prueba = todos.stream()
                .filter(todo -> nameFilter == null || todo.getText().toLowerCase().contains(nameFilter.toLowerCase()))
                .filter(todo -> priorityFilter == null || priorityFilter != 0 ? Objects.equals(todo.getPriority(), priorityFilter) : Objects.equals(todo.getPriority(), todo.getPriority()))
                .filter(todo -> filterDone == null || todo.isStatus().equals(filterDone))
                .collect(Collectors.toList());
        System.out.println(prueba);
        return prueba;
    }

    private List<ToDo> paginateTodos(List<ToDo> todos, Integer pagination, Integer pageSize){
        int totalTodos = todos.size();
        if (totalTodos < 10) return todos;
        int lowerBound = pagination*pageSize;
        int upperBound = (pagination+1)*pageSize;

        if(totalTodos < upperBound){ //Si hay menos todos que el valor total
            upperBound = totalTodos;
        }

        todos = todos.subList(lowerBound, upperBound);
        return todos;
    }

    private List<ToDo> orderTodos(List<ToDo> todos, Integer orderPriority){
        if(orderPriority == 1) return todos;
        else if(orderPriority == 3) {
            todos.sort(Comparator.comparingInt(ToDo::getPriority));
        }else if(orderPriority ==2){
            todos.sort(Comparator.comparingInt(ToDo::getPriority).reversed()); // cambiarlo para despues
        }
        return todos;
    }

    public ToDo createToDo(ToDo task) {
        //Validation !!!
        return myRepo.createToDo(task);
    }

    public ToDo updateToDo(ToDo task, Long id) {
        return myRepo.updateToDo(task, id);
    }

    public ToDo undoneToDo(Long id) {
        return myRepo.undoneToDo(id);
    }

    public ToDo getTodo(Long id) {
        return myRepo.getTodo(id);
    }

    public ToDo deleteToDo(Long id) {
        return myRepo.deleteToDo(id);
    }

    public ToDo doneToDo(Long id) {
        return myRepo.doneToDo(id);
    }
}
