package com.encora.ToDosBackend.controller;

import com.encora.ToDosBackend.model.ToDo;
import com.encora.ToDosBackend.service.ToDoService;
import com.encora.ToDosBackend.service.ToDoServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = ToDoController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ToDoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ToDoServiceImpl toDoService;

    @InjectMocks
    private ToDoController toDoController;

    @Autowired
    private ObjectMapper objectMapper;

    private List<ToDo> mockTodos;

    private ToDo newTodo;

    @BeforeEach
    public void init(){
        newTodo = new ToDo(1L, "Primera tarea de la semana", null, false, LocalDateTime.of(2025,1,31,22,10), 1, LocalDateTime.of(2025,1,10, 19, 20));
    }

    @Test
    public void TestCreateToDoEndpoint() throws Exception{
        given(toDoService.createToDo(ArgumentMatchers.any()))
                .willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions result = mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newTodo)));

        result.andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void TestGetAllToDosEndpoint() throws Exception{
        List<ToDo> ToDosResult = Arrays.asList();
        when(toDoService.getTodos("tarea", 1,"All",1, 1,1))
                .thenReturn(ToDosResult);

        ResultActions result = mockMvc.perform(get("/todos").contentType(MediaType.APPLICATION_JSON)
                .param("nameFilter", "tarea")
                .param("priorityFilter", "1")
                .param("filterDone", "All")
                .param("pagination", "1")
                .param("orderPriority", "1")
                .param("orderDate", "1"));

        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void TestGetToDoEndpoint() throws Exception{
        when(toDoService.getTodo(1L))
                .thenReturn(newTodo);

        ResultActions result = mockMvc.perform(get("/todos/1")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newTodo)));

        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is((newTodo.getId().intValue()))))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void TestGetUpdateToDoEndpoint() throws Exception{
        newTodo = new ToDo(1L, "Updated", null, false, LocalDateTime.of(2025,1,31,22,10), 1, LocalDateTime.of(2025,1,10, 19, 20));
        when(toDoService.updateToDo(newTodo,1L))
                .thenReturn(newTodo);

        ResultActions result = mockMvc.perform(put("/todos/1")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newTodo)));

        result.andExpect(MockMvcResultMatchers.status().isOk())
                //.andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is((newTodo.getId().intValue()))))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void TestDeleteToDoEndpoint() throws Exception{
        Boolean deleted = true;
        when(toDoService.updateToDo(newTodo,1L))
                .thenReturn(newTodo);

        ResultActions result = mockMvc.perform(delete("/todos/1")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newTodo)));

        result.andExpect(MockMvcResultMatchers.status().isOk());
                //.andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is((newTodo.getId().intValue()))))
    }

    @Test
    public void TestUndoneToDoEndpoint() throws Exception{
        when(toDoService.undoneToDo(1L)).thenAnswer(invocation -> {
            newTodo.setStatus(false);
            newTodo.setDoneDate(null);
            return newTodo;
        });

        ResponseEntity<ToDo> result = toDoController.undoneTask(1L);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertFalse(result.getBody().isStatus());
        assertNull(result.getBody().getDoneDate());
    }

    @Test
    public void TestDoneToDoEndpoint() throws Exception{
        when(toDoService.doneToDo(1L)).thenAnswer(invocation -> {
            newTodo.setStatus(true);
            newTodo.setDoneDate(LocalDateTime.of(2024,11,24,11,11,11));
            return newTodo;
        });

        ResponseEntity<ToDo> result = toDoController.doneTask(1L);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertTrue(result.getBody().isStatus());
        assertNotNull(result.getBody().getDoneDate());
    }

}
