package com.encora.ToDosBackend.service;

import com.encora.ToDosBackend.model.ToDo;

import java.util.List;

/**
 * Service interface defining business logic for managing ToDo tasks.
 */
public interface ToDoService {

    /**
     * Retrieves a list of ToDo items, with optional filtering, sorting, and pagination.
     *
     * @param nameFilter      Optional filter by task name.
     * @param priorityFilter  Optional filter by task priority.
     * @param filterDone      Optional filter by task status ("Done", "Undone", or null).
     * @param pagination      Page number for pagination (0-based index).
     * @param orderPriority   Sort order for priority: 1=ASC, 2=DESC.
     * @param orderDate       Sort order for due date: 1=ASC, 2=DESC.
     * @return List of ToDo items after applying filters and sorting.
     */
    List<ToDo> getTodos(String nameFilter, Integer priorityFilter, String filterDone, Integer pagination, Integer orderPriority, Integer orderDate);

    /**
     * Filters a list of ToDo items by name, priority, and status.
     *
     * @param todos           Original list of ToDo items.
     * @param nameFilter      Optional name filter.
     * @param priorityFilter  Optional priority filter.
     * @param filterDone      Optional status filter ("Done", "Undone", or null).
     * @return Filtered list of ToDo items.
     */
    List<ToDo> filterTodos(List<ToDo> todos, String nameFilter, Integer priorityFilter, String filterDone);

    /**
     * Paginates a list of ToDo items.
     *
     * @param todos       List of ToDo items to paginate.
     * @param pagination  Page index (0-based).
     * @param pageSize    Number of items per page.
     * @return Sublist of ToDo items for the specified page.
     */
    List<ToDo> paginateTodos(List<ToDo> todos, Integer pagination, Integer pageSize);

    /**
     * Orders a list of ToDo items by priority and/or due date.
     *
     * @param todos         List of ToDo items to sort.
     * @param orderPriority Priority sort order: 1=ASC, 2=DESC, null=no sort.
     * @param orderDate     Due date sort order: 1=ASC, 2=DESC, null=no sort.
     * @return Ordered list of ToDo items.
     */
    List<ToDo> orderTodos(List<ToDo> todos, Integer orderPriority , Integer orderDate);

    /**
     * Creates a new ToDo item.
     *
     * @param task The ToDo item to create.
     * @return The created ToDo item with generated fields.
     */
    ToDo createToDo(ToDo task);

    /**
     * Updates an existing ToDo item.
     *
     * @param task The updated task details.
     * @param id   The ID of the task to update.
     * @return The updated ToDo item.
     */
    ToDo updateToDo(ToDo task, Long id);

    /**
     * Marks a task as not done.
     *
     * @param id The ID of the task to mark as undone.
     * @return The updated ToDo item.
     */
    ToDo undoneToDo(Long id);

    /**
     * Retrieves a ToDo item by ID.
     *
     * @param id The ID of the task.
     * @return The requested ToDo item.
     */
    ToDo getTodo(Long id);

    /**
     * Deletes a ToDo item.
     *
     * @param id The ID of the task to delete.
     * @return True if successfully deleted; otherwise false.
     */
    Boolean deleteToDo(Long id);

    /**
     * Marks a task as done.
     *
     * @param id The ID of the task to mark as done.
     * @return The updated ToDo item.
     */
    ToDo doneToDo(Long id);
}