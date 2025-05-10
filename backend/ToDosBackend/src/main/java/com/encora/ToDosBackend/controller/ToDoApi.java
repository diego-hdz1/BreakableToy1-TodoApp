package com.encora.ToDosBackend.controller;

import com.encora.ToDosBackend.model.ToDo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API interface for managing ToDo tasks.
 * Provides endpoints to create, retrieve, update, mark as done/undone, and delete tasks.
 */
public interface ToDoApi {

    /**
     * Retrieves a list of ToDo items, optionally filtered and sorted.
     *
     * @param nameFilter Filter by task name (optional).
     * @param priorityFilter Filter by task priority (optional).
     * @param filterDone Filter by completion status: "true", "false", or null (optional).
     * @param pagination Optional pagination limit (e.g., number of items per page).
     * @param orderPriority Sorting order by priority: 1 for ascending, -1 for descending (optional).
     * @param orderDate Sorting order by creation date: 1 for ascending, -1 for descending (optional).
     * @return A list of ToDo items matching the criteria.
     */
    @GetMapping("/todos")
    ResponseEntity<List<ToDo>> getTodos(
            @RequestParam(required = false) String nameFilter,
            @RequestParam(required = false) Integer priorityFilter,
            @RequestParam(required = false) String filterDone,
            @RequestParam(required = false) Integer pagination,
            @RequestParam(required = false) Integer orderPriority,
            @RequestParam(required = false) Integer orderDate
    );

    /**
     * Retrieves a specific ToDo item by its ID.
     *
     * @param id The unique identifier of the task.
     * @return The requested ToDo item.
     */
    @GetMapping("/todos/{id}")
    ResponseEntity<ToDo> getTodo(@PathVariable(required = true) Long id);

    /**
     * Creates a new ToDo task.
     *
     * @param task The ToDo item to be created.
     * @return The created ToDo item with generated fields (e.g., ID).
     */
    @PostMapping("/todos")
    ResponseEntity<ToDo> createToDo(@RequestBody(required = true)ToDo task);

    /**
     * Updates an existing ToDo task.
     *
     * @param task The updated ToDo item.
     * @param id The ID of the task to update.
     * @return The updated ToDo item.
     */
    @PutMapping("/todos/{id}")
    ResponseEntity<ToDo> updateToDo(@RequestBody(required = true) ToDo task, @PathVariable(required = true)Long id);

    /**
     * Marks the specified task as done.
     *
     * @param id The ID of the task to mark as done.
     * @return The updated ToDo item with completion status set to true.
     */
    @PutMapping("/todos/{id}/done")
    ResponseEntity<ToDo> doneTask(@PathVariable(required = true)Long id);

    /**
     * Marks the specified task as not done.
     *
     * @param id The ID of the task to mark as not done.
     * @return The updated ToDo item with completion status set to false.
     */
    @PutMapping("/todos/{id}/undone")
    ResponseEntity<ToDo> undoneTask(@PathVariable(required = true)Long id);

    /**
     * Deletes the specified ToDo task.
     *
     * @param id The ID of the task to delete.
     * @return True if the task was successfully deleted, false otherwise.
     */
    @DeleteMapping("/todos/{id}")
    ResponseEntity<Boolean> deleteToDo(@PathVariable(required = true)Long id);
}