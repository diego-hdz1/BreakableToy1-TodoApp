package com.encora.ToDosBackend.service;

import com.encora.ToDosBackend.model.ToDo;
import com.encora.ToDosBackend.model.ToDoStats;
import com.encora.ToDosBackend.repo.ToDoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class ToDoStatsService {

    @Autowired
    ToDoRepo toDoRepo;

    public ToDoStats getStats() {
        List<ToDo> todos = toDoRepo.getTodos();
        for(ToDo todo : todos){
            System.out.println(todo.toString());
        }
        ToDoStats finalStats = new ToDoStats(0L,0L,0L,0L, todos.size()/10);
        int count = 0;
        for(ToDo todo : todos){
            if(todo.getDoneDate() == null) continue;
            Duration duration = Duration.between(todo.getCreationDate(),todo.getDoneDate());
            Long finalMinutes =  duration.toMinutes();
            finalStats.setAverageTotalTime(finalStats.getAverageTotalTime() + finalMinutes);
            if(todo.getPriority() == 1) finalStats.setAverageLowTime(finalStats.getAverageLowTime() + finalMinutes);
            else if(todo.getPriority() == 2) finalStats.setAverageMediumTime(finalStats.getAverageMediumTime() + finalMinutes);
            else if(todo.getPriority() == 3) finalStats.setAverageHighTime(finalStats.getAverageHighTime() + finalMinutes);
            count = count + 1;
        }
        if(count==0) count = 1;
        finalStats.setAverageTotalTime(finalStats.getAverageTotalTime()/count);
        System.out.println(finalStats.toString());
        return finalStats;
    }
}
