package com.encora.ToDosBackend.service;

import com.encora.ToDosBackend.model.ToDo;
import com.encora.ToDosBackend.model.ToDoStats;
import com.encora.ToDosBackend.repo.ToDoRepositoryInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ToDoStatsServiceTest {

    private ToDoRepositoryInterface toDoRepo;
    private ToDoStatsService toDoStatsService;

    @BeforeEach
    void setUp() {
        toDoRepo = mock(ToDoRepositoryInterface.class);
        toDoStatsService = new ToDoStatsService(toDoRepo);
    }

    private ToDo createToDo(LocalDateTime created, LocalDateTime done, int priority) {
        ToDo todo = mock(ToDo.class);
        when(todo.getCreationDate()).thenReturn(created);
        when(todo.getDoneDate()).thenReturn(done);
        when(todo.getPriority()).thenReturn(priority);
        return todo;
    }

    @Test
    void testGetStatsWithNoToDos() {
        when(toDoRepo.getTodos()).thenReturn(List.of());

        ToDoStats stats = toDoStatsService.getStats();

        assertEquals(0, stats.getAverageTotalTime());
        assertEquals(0, stats.getAverageLowTime());
        assertEquals(0, stats.getAverageMediumTime());
        assertEquals(0, stats.getAverageHighTime());
        assertEquals(0, stats.getNumberPages());
    }

    @Test
    void testGetStatsWithToDosHavingNullDoneDates() {
        ToDo todo = createToDo(LocalDateTime.now().minusDays(1), null, 1);
        when(toDoRepo.getTodos()).thenReturn(List.of(todo));

        ToDoStats stats = toDoStatsService.getStats();

        assertEquals(0, stats.getAverageTotalTime());
        assertEquals(0, stats.getAverageLowTime());
        assertEquals(0, stats.getAverageMediumTime());
        assertEquals(0, stats.getAverageHighTime());
        assertEquals(0, stats.getNumberPages());
    }

    @Test
    void testGetStatsWithVariousPriorities() {
        LocalDateTime now = LocalDateTime.now();
        ToDo low = createToDo(now.minusMinutes(60), now, 1);
        ToDo medium = createToDo(now.minusMinutes(30), now, 2);
        ToDo high = createToDo(now.minusMinutes(90), now, 3);

        when(toDoRepo.getTodos()).thenReturn(List.of(low, medium, high));

        ToDoStats stats = toDoStatsService.getStats();

        assertEquals(60, stats.getAverageTotalTime());
        assertEquals(60, stats.getAverageLowTime());
        assertEquals(30, stats.getAverageMediumTime());
        assertEquals(90, stats.getAverageHighTime());
        assertEquals(0, stats.getNumberPages());
    }

    @Test
    void testGetStatsWithMultipleItemsSamePriority() {
        LocalDateTime now = LocalDateTime.now();
        ToDo t1 = createToDo(now.minusMinutes(20), now, 1);
        ToDo t2 = createToDo(now.minusMinutes(40), now, 1);

        when(toDoRepo.getTodos()).thenReturn(List.of(t1, t2));

        ToDoStats stats = toDoStatsService.getStats();

        assertEquals(30, stats.getAverageTotalTime());
        assertEquals(30, stats.getAverageLowTime());
        assertEquals(0, stats.getAverageMediumTime());
        assertEquals(0, stats.getAverageHighTime());
        assertEquals(0, stats.getNumberPages());
    }

    @Test
    void testGetStatsWithMoreThan10Todos() {
        LocalDateTime now = LocalDateTime.now();
        List<ToDo> todos = java.util.stream.IntStream.range(0, 25)
                .mapToObj(i -> createToDo(now.minusMinutes(10), now, 1))
                .toList();

        when(toDoRepo.getTodos()).thenReturn(todos);

        ToDoStats stats = toDoStatsService.getStats();

        assertEquals(10, stats.getAverageTotalTime());
        assertEquals(10, stats.getAverageLowTime());
        assertEquals(0, stats.getAverageMediumTime());
        assertEquals(0, stats.getAverageHighTime());
        assertEquals(2, stats.getNumberPages());
    }
}
