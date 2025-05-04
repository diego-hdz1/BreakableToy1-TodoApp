package com.encora.ToDosBackend.repo;

import com.encora.ToDosBackend.model.ToDo;

import java.util.List;

/**
 * Repository interface for ToDo persistence operations.
 * Defines CRUD and state-change operations on ToDo tasks.
 */
public interface ToDoRepositoryInterface {

    /**
     * Retrieves all ToDo items.
     *
     * @return List of all stored ToDo tasks.
     */
    List<ToDo> getTodos();

    /**
     * Creates and stores a new ToDo task.
     *
     * @param task The ToDo task to be created.
     * @return The created task with an assigned ID.
     */
    ToDo createToDo(ToDo task);

    /**
     * Generates a new unique ID for a ToDo task.
     *
     * @return A unique identifier not currently in use.
     */
    Long generateId();

    /**
     * Updates an existing ToDo task with new values.
     *
     * @param task Updated task data.
     * @param id   ID of the task to update.
     * @return The updated ToDo task.
     */
    ToDo updateToDo(ToDo task, Long id);

    /**
     * Marks the specified task as done and sets the done date.
     *
     * @param id ID of the task to mark as done.
     * @return The updated task with status and done date.
     */
    ToDo doneToDo(Long id);

    /**
     * Marks the specified task as not done and clears the done date.
     *
     * @param id ID of the task to mark as not done.
     * @return The updated task.
     */
    ToDo undoneToDo(Long id);

    /**
     * Retrieves a specific ToDo task by ID.
     *
     * @param id ID of the task.
     * @return The found ToDo task.
     */
    ToDo getTodo(Long id);

    /**
     * Deletes a ToDo task by ID.
     *
     * @param id ID of the task to delete.
     * @return True if the task was successfully removed, false otherwise.
     */
    Boolean deleteToDo(Long id);

    /**
     * Searches for a ToDo task by ID.
     *
     * @param id ID of the task to find.
     * @return The matching ToDo task.
     */
    ToDo findToDoById(Long id);
}
