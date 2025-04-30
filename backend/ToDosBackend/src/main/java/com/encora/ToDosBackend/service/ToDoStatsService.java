package com.encora.ToDosBackend.service;

import com.encora.ToDosBackend.model.ToDo;
import com.encora.ToDosBackend.model.ToDoStats;
import com.encora.ToDosBackend.repo.ToDoRepo;
import com.encora.ToDosBackend.repo.ToDoRepoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class ToDoStatsService {

    final ToDoRepoInterface toDoRepo;

    public ToDoStatsService(ToDoRepoInterface toDoRepo){
        this.toDoRepo = toDoRepo;
    }

    public ToDoStats getStats() {
        List<ToDo> todos = toDoRepo.getTodos();

        long totalTime = 0L;
        long lowTime = 0L, mediumTime = 0L, highTime = 0L;
        int totalCount = 0, lowCount = 0, mediumCount = 0, highCount = 0;

        for (ToDo todo : todos) {
            if (todo.getDoneDate() == null) continue;

            long minutes = Duration.between(todo.getCreationDate(), todo.getDoneDate()).toMinutes();
            totalTime += minutes;
            totalCount++;

            switch (todo.getPriority()) {
                case 1 -> {
                    lowTime += minutes;
                    lowCount++;
                }
                case 2 -> {
                    mediumTime += minutes;
                    mediumCount++;
                }
                case 3 -> {
                    highTime += minutes;
                    highCount++;
                }
            }
        }

        long avgTotal = totalCount > 0 ? totalTime / totalCount : 0;
        long avgLow = lowCount > 0 ? lowTime / lowCount : 0;
        long avgMedium = mediumCount > 0 ? mediumTime / mediumCount : 0;
        long avgHigh = highCount > 0 ? highTime / highCount : 0;
        return new ToDoStats(avgTotal, avgLow, avgMedium, avgHigh, todos.size() / 10);
    }
}
