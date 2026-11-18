
package com.sumpositive.ticketing.task.service;

import java.util.List;

import com.sumpositive.ticketing.task.dto.TaskRequest;
import com.sumpositive.ticketing.task.dto.TaskResponse;
import com.sumpositive.ticketing.task.entity.TaskStatus;

public interface TaskService {

    TaskResponse createTask(TaskRequest request);

    TaskResponse getTaskById(Long taskId);

    List<TaskResponse> getAllTasks();

    List<TaskResponse> getMyTasks();

    List<TaskResponse> getTasksByStatus(TaskStatus status);

    TaskResponse updateTask(Long taskId, TaskRequest request);

    TaskResponse updateTaskStatus(Long taskId, TaskStatus status);

    void deleteTask(Long taskId);
    
    TaskResponse updateMyTaskStatus(Long taskId, TaskStatus status);
}
