package com.encora.ToDosBackend.service;

import com.encora.ToDosBackend.model.ToDo;
import com.encora.ToDosBackend.repo.ToDoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ToDoService {

    @Autowired
    ToDoRepo toDoRepo;

    public List<ToDo> getTodos(String nameFilter, Integer priorityFilter, String filterDone, Integer pagination, Integer orderPriority, Integer orderDate) {
        List<ToDo> todos = toDoRepo.getTodos();
//        System.out.println(nameFilter);
//        System.out.println(priorityFilter);
//        System.out.println(filterDone);
//        System.out.println(pagination);
//        System.out.println(orderPriority);
        todos = filterTodos(todos, nameFilter, priorityFilter, filterDone);
        todos = orderTodos(todos, orderPriority, orderDate);
        todos = paginateTodos(todos, pagination, 10);
        return todos;
    }

    private List<ToDo> filterTodos(List<ToDo> todos, String nameFilter, Integer priorityFilter, String filterDone) {
        if(nameFilter == null && priorityFilter == null && filterDone == null) return todos; //Cehcar el filterDone
        Boolean checkFilter;
        //if(filterDone == "All") checkFilter = null;
        if(Objects.equals(filterDone, "Done")) checkFilter = false;
        else if(Objects.equals(filterDone, "Undone")) checkFilter = true;
        else {
            checkFilter = null;
        }
        System.out.println(checkFilter);
//        System.out.println(todos.get(0).toString());
//        System.out.println(nameFilter);
//        System.out.println(priorityFilter);
//        System.out.println(filterDone);
         List<ToDo> prueba = todos.stream()
                .filter(todo -> nameFilter == null || todo.getText().toLowerCase().contains(nameFilter.toLowerCase()))
                .filter(todo -> priorityFilter == null || priorityFilter != 0 ? Objects.equals(todo.getPriority(), priorityFilter) : Objects.equals(todo.getPriority(), todo.getPriority()))
                 .filter(todo -> checkFilter == null || todo.isStatus().equals(checkFilter))
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

    private List<ToDo> orderTodos(List<ToDo> todos, Integer orderPriority , Integer orderDate){
        if(orderPriority == 1 && orderDate == 1) return todos;
        else if(orderPriority == 3) {
            todos.sort(Comparator.comparingInt(ToDo::getPriority));
        }else if(orderPriority ==2){
            todos.sort(Comparator.comparingInt(ToDo::getPriority).reversed()); // cambiarlo para despues
        }

        //CHECAR BIEN QUE SE PUEDAN LOS DOS AL MISMO TIEMPO
        if(orderDate == 3){
            todos.sort(Comparator.comparing(ToDo::getDueDate, Comparator.nullsLast(LocalDate::compareTo)));
            //todos.sort((t1,t2)->t1.getDueDate().compareTo(t2.getDueDate()));
        }else if(orderDate ==2){
            todos.sort(Comparator.comparing(ToDo::getDueDate, Comparator.nullsFirst(LocalDate::compareTo)).reversed());
        }
        return todos;
    }

    public ToDo createToDo(ToDo task) {
        //Validation !!!
        return toDoRepo.createToDo(task);
    }

    public ToDo updateToDo(ToDo task, Long id) {
        return toDoRepo.updateToDo(task, id);
    }

    public ToDo undoneToDo(Long id) {
        return toDoRepo.undoneToDo(id);
    }

    public ToDo getTodo(Long id) {
        return toDoRepo.getTodo(id);
    }

    public Boolean deleteToDo(Long id) {
        return toDoRepo.deleteToDo(id);
    }

    public ToDo doneToDo(Long id) {
        return toDoRepo.doneToDo(id);
    }
}
