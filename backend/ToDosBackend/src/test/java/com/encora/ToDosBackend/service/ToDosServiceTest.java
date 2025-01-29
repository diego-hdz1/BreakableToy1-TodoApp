package com.encora.ToDosBackend.service;

import com.encora.ToDosBackend.ToDosBackendApplication;
import com.encora.ToDosBackend.model.ToDo;
import com.encora.ToDosBackend.model.ToDoStats;
import com.encora.ToDosBackend.repo.ToDoRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootTest(classes = ToDosBackendApplication.class)
public class ToDosServiceTest {
    @Mock
    private ToDoRepo toDoRepo;

    @InjectMocks
    private ToDoServiceImpl toDoService;

    @InjectMocks
    private ToDoStatsService toDoStatsService;

    private List<ToDo> mockTodos;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);

        mockTodos = Arrays.asList(
                new ToDo(1L, "Primera tarea de la semana", null, false, LocalDateTime.of(2025,1,31,22,10), 1, LocalDateTime.of(2025,1,10, 19, 20)),
                new ToDo(2L, "Segunda tarea de la semana", LocalDate.of(2025,1,31), false, LocalDateTime.of(2025,1,31,22,10), 2, LocalDateTime.of(2025,1,11, 11,35)),
                new ToDo(3L, "Tercera tarea de la semana", LocalDate.of(2025,2,1), false, LocalDateTime.of(2025,1,31,22,10), 3, LocalDateTime.of(2025,1,12,17, 11))
        );
    }

    @Test
    void testFilterTodosByPriority(){
        List<ToDo> filtered = toDoService.filterTodos(mockTodos, null,3,null);
        assertEquals(1, filtered.size());
        assertEquals("Tercera tarea de la semana",filtered.get(0).getText());
    }

    @Test
    void testFilterTodosByName(){
        List<ToDo> filtered = toDoService.filterTodos(mockTodos, "Segunda tarea de la semana",2,null);
        assertEquals(1, filtered.size());
        assertEquals("Segunda tarea de la semana",filtered.get(0).getText());
    }

    @Test
    void testFilterTodosByStatus(){
        List<ToDo> filtered = toDoService.filterTodos(mockTodos, null,3,"Done");
        assertEquals(1, filtered.size());
        assertEquals(false,filtered.get(0).isStatus());
    }

    @Test
    void testPaginateToDosOutOfBounds(){
        List<ToDo> paginated = toDoService.paginateTodos(mockTodos, 100, 10);
        assertEquals(mockTodos.size(), paginated.size());
    }

    @Test
    void testPaginateToDosInBounds(){
        List<ToDo> paginated = toDoService.paginateTodos(mockTodos, 1, 10);
        assertEquals(3, paginated.size());
    }

    @Test
    void testOrderByPriorityAsc(){
        List<ToDo> Ordered = toDoService.orderTodos(mockTodos, 2, 1);
        assertEquals(3, Ordered.get(0).getPriority());
    }

    @Test
    void testOrderByPriorityDesc(){
        List<ToDo> Ordered = toDoService.orderTodos(mockTodos, 3, 1);
        assertEquals(1, Ordered.get(0).getPriority());
    }

    @Test
    void testOrderByDateDesc(){
        List<ToDo> Ordered = toDoService.orderTodos(mockTodos, 1, 2);
        assertEquals(LocalDate.of(2025,2,1), Ordered.get(0).getDueDate());
    }

    @Test
    void testOrderByDateDescAndOrderDateDesc(){
        List<ToDo> Ordered = toDoService.orderTodos(mockTodos, 2, 2);
        assertEquals(LocalDate.of(2025,2,1), Ordered.get(0).getDueDate());
    }

    @Test
    void testOrderByDateDescAndOrderDateAsc(){
        List<ToDo> Ordered = toDoService.orderTodos(mockTodos, 2, 3);
        assertEquals(LocalDate.of(2025,2,1), Ordered.get(0).getDueDate());
    }

    @Test
    void testOrderByDateAscAndOrderDateAsc(){
        List<ToDo> Ordered = toDoService.orderTodos(mockTodos, 3, 3);
        assertNull(Ordered.get(0).getDueDate());
    }

    @Test
    void testDoneToDo(){
        ToDo existingToDo = new ToDo();
        existingToDo.setId(100L);
        existingToDo.setDoneDate(null);
        existingToDo.setStatus(false);
        when(toDoRepo.doneToDo(100L)).thenReturn(existingToDo);
        ToDo result = toDoService.doneToDo(100L);
        assertNotNull(result);
        assertEquals(result.getId(), existingToDo.getId());
        verify(toDoRepo, times(1)).doneToDo(100L);


    }

    @Test
    void testUndoneToDo(){
        ToDo existingToDo = new ToDo();
        existingToDo.setId(100L);
        existingToDo.setDoneDate(LocalDateTime.of(2024,1,1,1,1));
        existingToDo.setStatus(true);
        when(toDoRepo.undoneToDo(100L)).thenReturn(existingToDo);
        ToDo result = toDoService.undoneToDo(100L);
        assertNotNull(result);
        assertEquals(result.getId(), existingToDo.getId());
        assertNull(result.getDueDate());
        verify(toDoRepo, times(1)).undoneToDo(100L);
    }

    @Test
    void getToDo(){
        ToDo existingToDo = new ToDo();
        existingToDo.setId(100L);
        when(toDoRepo.getTodo(100L)).thenReturn(existingToDo);
        ToDo result = toDoService.getTodo(100L);
        assertNotNull(result);
        assertEquals(result.getId(), existingToDo.getId());
        verify(toDoRepo, times(1)).getTodo(100L);
    }


    @Test
    void testOrderByPriorityAndDateDesc(){
        List<ToDo> Ordered = toDoService.orderTodos(mockTodos, 2, 2);
        assertEquals(3, Ordered.get(0).getPriority());
        assertEquals(LocalDate.of(2025,2,1), Ordered.get(0).getDueDate());
    }

    @Test
    void testCreateToDo(){
        ToDo newToDo = new ToDo(4L, "New", LocalDate.of(2025,3,19), true, LocalDateTime.of(2025,2,11,11,11), 2, LocalDateTime.of(2025,1,12,17, 11));
        when(toDoRepo.createToDo(newToDo)).thenReturn(newToDo);

        ToDo result = toDoService.createToDo(newToDo);
        assertNotNull(result);
        assertEquals("New", result.getText());
        assertEquals(4L, result.getId());
    }

    @Test
    void testUpdateToDo(){
        ToDo newToDo = new ToDo(1L, "Primera tarea de la semana", null, false, LocalDateTime.of(2025,1,31,22,10), 1, LocalDateTime.of(2025,1,10, 19, 20));
        when(toDoRepo.updateToDo(newToDo, 1L)).thenReturn(newToDo);

        newToDo.setText("Updated to do");
        ToDo result = toDoService.updateToDo(newToDo, 1L);
        assertNotNull(result);
        assertEquals("Updated to do", result.getText());
    }

    @Test
    void testDeleteToDo(){
        when(toDoRepo.deleteToDo(1L)).thenReturn(true);
        Boolean result = toDoService.deleteToDo(1L);
        assertEquals(true, result);
    }

    @Test
    void testStatsService(){
        when(toDoRepo.getTodos()).thenReturn(mockTodos);
        ToDoStats finalStats  = toDoStatsService.getStats();
        assertNotNull(finalStats);
    }

    @Test
    void testValidationException(){
        String error = "This is a validation error";
        ValidationException exception = new ValidationException(error);
        assertEquals(error, exception.getMessage());
    }

}
