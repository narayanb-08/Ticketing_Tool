
package com.sumpositive.ticketing.task.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sumpositive.ticketing.project.entity.Project;
import com.sumpositive.ticketing.project.repository.ProjectRepository;
import com.sumpositive.ticketing.task.dto.TaskRequest;
import com.sumpositive.ticketing.task.dto.TaskResponse;
import com.sumpositive.ticketing.task.entity.Task;
import com.sumpositive.ticketing.task.entity.TaskStatus;
import com.sumpositive.ticketing.task.repository.TaskRepository;
import com.sumpositive.ticketing.ticket.entity.Ticket;
import com.sumpositive.ticketing.ticket.repository.TicketRepository;
import com.sumpositive.ticketing.user.entity.User;
import com.sumpositive.ticketing.user.enums.AccountStatus;
import com.sumpositive.ticketing.user.enums.UserRole;
import com.sumpositive.ticketing.user.repository.UserRepository;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

	private final TaskRepository taskRepository;
	private final UserRepository userRepository;
	private final TicketRepository ticketRepository;
	private final ProjectRepository projectRepository;

	public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository,
			TicketRepository ticketRepository, ProjectRepository projectRepository) {

		this.taskRepository = taskRepository;
		this.userRepository = userRepository;
		this.ticketRepository = ticketRepository;
		this.projectRepository = projectRepository;
	}

	@Override
	public TaskResponse createTask(TaskRequest request) {

		User currentUser = getCurrentUser();

		Task task = new Task();

		task.setTitle(request.getTitle());
		task.setDescription(request.getDescription());
		task.setPriority(request.getPriority());
		task.setDueDate(request.getDueDate());
		task.setStatus(TaskStatus.OPEN);
		task.setCreatedBy(currentUser);

		
		if (request.getProjectId() != null) {

		    Project project = projectRepository.findById(request.getProjectId())
		            .orElseThrow(() ->
		                    new IllegalArgumentException("Project not found"));

		    task.setProject(project);
		}
		
		if (request.getAssignedToId() != null) {
			task.setAssignedTo(getValidAssignedUser(request.getAssignedToId()));
		}

		if (request.getTicketId() != null) {

			Ticket ticket = ticketRepository.findById(request.getTicketId())
					.orElseThrow(() -> new IllegalArgumentException("Ticket not found"));

			task.setTicket(ticket);
		}
		
		

		return mapToResponse(taskRepository.save(task));
	}

	@Override
	@Transactional(readOnly = true)
	public TaskResponse getTaskById(Long taskId) {

		Task task = findTask(taskId);

		return mapToResponse(task);
	}

	@Override
	@Transactional(readOnly = true)
	public List<TaskResponse> getAllTasks() {

		return taskRepository.findAll().stream().map(this::mapToResponse).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<TaskResponse> getMyTasks() {

		User currentUser = getCurrentUser();

		return taskRepository.findByAssignedTo(currentUser).stream().map(this::mapToResponse).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<TaskResponse> getTasksByStatus(TaskStatus status) {

		return taskRepository.findByStatus(status).stream().map(this::mapToResponse).toList();
	}

	@Override
	public TaskResponse updateTask(Long taskId, TaskRequest request) {

		Task task = findTask(taskId);

		task.setTitle(request.getTitle());
		task.setDescription(request.getDescription());
		task.setPriority(request.getPriority());
		task.setDueDate(request.getDueDate());

		if (request.getProjectId() != null) {

		    Project project = projectRepository.findById(request.getProjectId())
		            .orElseThrow(() ->
		                    new IllegalArgumentException("Project not found"));

		    task.setProject(project);
		}
		
		if (request.getAssignedToId() != null) {
			task.setAssignedTo(getValidAssignedUser(request.getAssignedToId()));
		} else {
			task.setAssignedTo(null);
		}

		if (request.getTicketId() != null) {

			Ticket ticket = ticketRepository.findById(request.getTicketId())
					.orElseThrow(() -> new IllegalArgumentException("Ticket not found"));

			task.setTicket(ticket);

		} else {
			task.setTicket(null);
		}

		task.setUpdatedAt(LocalDateTime.now());

		return mapToResponse(taskRepository.save(task));
	}

	@Override
	public TaskResponse updateTaskStatus(Long taskId, TaskStatus status) {

		Task task = findTask(taskId);

		task.setStatus(status);
		task.setUpdatedAt(LocalDateTime.now());

		return mapToResponse(taskRepository.save(task));
	}

	@Override
	public void deleteTask(Long taskId) {

		Task task = findTask(taskId);

		taskRepository.delete(task);
	}

	private Task findTask(Long taskId) {

		return taskRepository.findById(taskId)
				.orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + taskId));
	}

	private User getCurrentUser() {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		return userRepository.findByEmail(email)
				.orElseThrow(() -> new IllegalArgumentException("Logged-in user not found"));
	}

	private TaskResponse mapToResponse(Task task) {

		TaskResponse response = new TaskResponse();

		response.setId(task.getId());
		response.setTitle(task.getTitle());
		response.setDescription(task.getDescription());
		response.setStatus(task.getStatus());
		response.setPriority(task.getPriority());
		response.setDueDate(task.getDueDate());

		if (task.getAssignedTo() != null) {
			response.setAssignedToId(task.getAssignedTo().getId());
			response.setAssignedToName(task.getAssignedTo().getFullName());
		}

		if (task.getCreatedBy() != null) {
			response.setCreatedById(task.getCreatedBy().getId());
			response.setCreatedByName(task.getCreatedBy().getFullName());
		}
		if (task.getTicket() != null) {
			response.setTicketId(task.getTicket().getId());
		}
		
		if (task.getProject() != null) {

		    response.setProjectId(task.getProject().getId());

		    response.setProjectName(
		            task.getProject().getProjectName());

		    response.setProjectKey(
		            task.getProject().getProjectKey());
		}

		response.setCreatedAt(task.getCreatedAt());
		response.setUpdatedAt(task.getUpdatedAt());

		return response;
	}

	@Override
	public TaskResponse updateMyTaskStatus(Long taskId, TaskStatus status) {

		User currentUser = getCurrentUser();

		Task task = findTask(taskId);

		if (task.getAssignedTo() == null || !task.getAssignedTo().getId().equals(currentUser.getId())) {
			throw new org.springframework.security.access.AccessDeniedException(
					"You can only update tasks assigned to you");
		}

		task.setStatus(status);
		task.setUpdatedAt(LocalDateTime.now());

		return mapToResponse(taskRepository.save(task));
	}

	private User getValidAssignedUser(Long userId) {

		User assignedUser = userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("Assigned user not found"));

		if (!assignedUser.isActive() || assignedUser.getAccountStatus() != AccountStatus.APPROVED) {

			throw new IllegalArgumentException("Cannot assign task to an inactive or unapproved user");
		}

		if (assignedUser.getUserRole() == null || assignedUser.getUserRole() == UserRole.HR) {

			throw new IllegalArgumentException("This user role is not allowed to receive tasks");
		}

		return assignedUser;
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public List<TaskResponse> getTasksByProject(Long projectId) {

	    return taskRepository.findByProjectId(projectId)
	            .stream()
	            .map(this::mapToResponse)
	            .toList();
	}
}
