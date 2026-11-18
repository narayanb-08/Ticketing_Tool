package com.sumpositive.ticketing.task.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sumpositive.ticketing.task.entity.Task;
import com.sumpositive.ticketing.task.entity.TaskStatus;
import com.sumpositive.ticketing.user.entity.User;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByAssignedTo(User assignedTo);

    List<Task> findByCreatedBy(User createdBy);

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByAssignedToAndStatus(User assignedTo, TaskStatus status);
}
