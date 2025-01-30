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
public class ToDoServiceImpl implements ToDoService {

    @Autowired
    ToDoRepo toDoRepo;

    @Override
    public List<ToDo> getTodos(String nameFilter, Integer priorityFilter, String filterDone, Integer pagination, Integer orderPriority, Integer orderDate) {
        List<ToDo> todos = toDoRepo.getTodos();
        todos = filterTodos(todos, nameFilter, priorityFilter, filterDone);
        todos = orderTodos(todos, orderPriority, orderDate);
        todos = paginateTodos(todos, pagination, 10);
        return todos;
    }

    @Override
    public List<ToDo> filterTodos(List<ToDo> todos, String nameFilter, Integer priorityFilter, String filterDone) {
        if(nameFilter == null && priorityFilter == null && filterDone == null) return todos;
        Boolean checkFilter;
        if(Objects.equals(filterDone, "Done")) checkFilter = false;
        else if(Objects.equals(filterDone, "Undone")) checkFilter = true;
        else {
            checkFilter = null;
        }
         List<ToDo> filteredToDos = todos.stream()
                .filter(todo -> nameFilter == null || todo.getText().toLowerCase().contains(nameFilter.toLowerCase()))
                .filter(todo -> priorityFilter == null || priorityFilter != 0 ? Objects.equals(todo.getPriority(), priorityFilter) : Objects.equals(todo.getPriority(), todo.getPriority()))
                 .filter(todo -> checkFilter == null || todo.isStatus().equals(checkFilter))
                .collect(Collectors.toList());
        return filteredToDos;
    }

    @Override
    public List<ToDo> paginateTodos(List<ToDo> todos, Integer pagination, Integer pageSize){
        int totalTodos = todos.size();
        if (totalTodos < 10) return todos;
        int lowerBound = pagination*pageSize;
        int upperBound = (pagination+1)*pageSize;

        if(totalTodos < upperBound){
            upperBound = totalTodos;
        }

        todos = todos.subList(lowerBound, upperBound);
        return todos;
    }

    @Override
    public List<ToDo> orderTodos(List<ToDo> todos, Integer orderPriority , Integer orderDate){
        if(orderPriority == 1 && orderDate == 1) return todos;

        if(orderPriority == 3 && orderDate ==1) {todos.sort(Comparator.comparingInt(ToDo::getPriority));}
        else if(orderPriority ==2 && orderDate == 1) {todos.sort(Comparator.comparingInt(ToDo::getPriority).reversed());}
        else if(orderPriority == 1 && orderDate == 3) {todos.sort(Comparator.comparing(ToDo::getDueDate, Comparator.nullsLast(LocalDate::compareTo)));}
        else if(orderPriority ==1 && orderDate ==2) {todos.sort(Comparator.comparing(ToDo::getDueDate, Comparator.nullsFirst(LocalDate::compareTo)).reversed());}
        else if(orderPriority == 2 && orderDate == 2){
            todos.sort(Comparator.comparing(ToDo::getPriority).reversed()
                    .thenComparing(ToDo::getDueDate, Comparator.nullsLast(LocalDate::compareTo)));
        }else if(orderPriority == 2 && orderDate == 3){
            todos.sort(Comparator.comparing(ToDo::getPriority)
                    .thenComparing(ToDo::getDueDate, Comparator.nullsFirst(LocalDate::compareTo)).reversed());
        }else if(orderPriority == 3 && orderDate ==2){
            todos.sort(Comparator.comparing(ToDo::getPriority).reversed()
                    .thenComparing(ToDo::getDueDate, Comparator.nullsFirst(LocalDate::compareTo)));
        }else if(orderPriority == 3 && orderDate == 3){
            todos.sort(Comparator.comparingInt(ToDo::getPriority)
                    .thenComparing(ToDo::getDueDate, Comparator.nullsLast(LocalDate::compareTo).reversed()));
        }
        return todos;
    }

    @Override
    public ToDo createToDo(ToDo task) {
        validateToDo(task);
        return toDoRepo.createToDo(task);
    }

    @Override
    public ToDo updateToDo(ToDo task, Long id) {
        validateToDo(task);
        return toDoRepo.updateToDo(task, id);
    }

    public void validateToDo(ToDo task){
        if(task.getText() == null || task.getText().isEmpty()){
            throw new ValidationException("Name cannot be empty");
        }
        if(task.getPriority() == null){
            throw new ValidationException("Priority cannot be empty");
        }
        if(task.isStatus() == null){
            throw new ValidationException("Status cannot be empty");
        }
        if(task.getDueDate() != null){
            LocalDate currentDate = LocalDate.now();
            if(currentDate.isAfter(task.getDueDate())){
                throw new ValidationException("Due date cannot be a date that has already passed");
            }
        }
    }


    @Override
    public ToDo undoneToDo(Long id) {
        return toDoRepo.undoneToDo(id);
    }

    @Override
    public ToDo getTodo(Long id) {
        return toDoRepo.getTodo(id);
    }

    @Override
    public Boolean deleteToDo(Long id) {
        return toDoRepo.deleteToDo(id);
    }

    @Override
    public ToDo doneToDo(Long id) {
        return toDoRepo.doneToDo(id);
    }
}
