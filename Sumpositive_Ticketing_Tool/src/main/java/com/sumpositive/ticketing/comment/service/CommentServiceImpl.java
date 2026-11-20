package com.sumpositive.ticketing.comment.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sumpositive.ticketing.comment.dto.CommentResponse;
import com.sumpositive.ticketing.comment.dto.CreateCommentRequest;
import com.sumpositive.ticketing.comment.entity.Comment;
import com.sumpositive.ticketing.comment.entity.CommentType;
import com.sumpositive.ticketing.comment.repository.CommentRepository;
import com.sumpositive.ticketing.task.entity.Task;
import com.sumpositive.ticketing.task.repository.TaskRepository;
import com.sumpositive.ticketing.ticket.entity.Ticket;
import com.sumpositive.ticketing.ticket.repository.TicketRepository;
import com.sumpositive.ticketing.user.entity.User;
import com.sumpositive.ticketing.user.enums.UserRole;
import com.sumpositive.ticketing.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

	private final CommentRepository commentRepository;
	private final UserRepository userRepository;
	private final TicketRepository ticketRepository;
	private final TaskRepository taskRepository;

	@Override
	public CommentResponse addComment(CreateCommentRequest request) {

		if ((request.getTicketId() == null && request.getTaskId() == null)
				|| (request.getTicketId() != null && request.getTaskId() != null)) {
			throw new RuntimeException("Provide either ticketId or taskId, not both");
		}

		User author = getCurrentUser();
		if (request.getType() == CommentType.INTERNAL
		        && author.getUserRole() == UserRole.EMPLOYEE) {
		    throw new RuntimeException(
		            "Employees cannot create internal notes");
		}

		Comment comment = new Comment();
		comment.setContent(request.getContent());
		comment.setType(request.getType() == null ? CommentType.PUBLIC : request.getType());
		comment.setAuthor(author);

		if (request.getTicketId() != null) {
			Ticket ticket = ticketRepository.findById(request.getTicketId())
					.orElseThrow(() -> new RuntimeException("Ticket not found"));
			comment.setTicket(ticket);
		}

		if (request.getTaskId() != null) {
			Task task = taskRepository.findById(request.getTaskId())
					.orElseThrow(() -> new RuntimeException("Task not found"));
			comment.setTask(task);
		}

		return mapToResponse(commentRepository.save(comment));
	}

	@Override
	@Transactional(readOnly = true)
	public List<CommentResponse> getTicketComments(Long ticketId) {

		User currentUser = getCurrentUser();

		return commentRepository.findByTicketIdOrderByCreatedAtAsc(ticketId).stream().filter(
				comment -> currentUser.getUserRole() != UserRole.EMPLOYEE || comment.getType() == CommentType.PUBLIC)
				.map(this::mapToResponse).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<CommentResponse> getTaskComments(Long taskId) {

		User currentUser = getCurrentUser();

		return commentRepository.findByTaskIdOrderByCreatedAtAsc(taskId).stream().filter(
				comment -> currentUser.getUserRole() != UserRole.EMPLOYEE || comment.getType() == CommentType.PUBLIC)
				.map(this::mapToResponse).toList();
	}

	@Override
	public CommentResponse updateComment(Long commentId, String content) {

		User currentUser = getCurrentUser();

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new RuntimeException("Comment not found"));

		if (!comment.getAuthor().getId().equals(currentUser.getId()) && currentUser.getUserRole() != UserRole.ADMIN) {
			throw new RuntimeException("You are not authorized to update this comment");
		}

		if (content == null || content.isBlank() || content.length() > 2000) {
			throw new IllegalArgumentException("Comment must contain 1 to 2000 characters");
		}

		comment.setContent(content);

		return mapToResponse(commentRepository.save(comment));
	}

	@Override
	public void deleteComment(Long commentId) {

		User currentUser = getCurrentUser();

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new RuntimeException("Comment not found"));

		if (!comment.getAuthor().getId().equals(currentUser.getId()) && currentUser.getUserRole() != UserRole.ADMIN) {
			throw new RuntimeException("You are not authorized to delete this comment");
		}

		commentRepository.delete(comment);
	}

	private CommentResponse mapToResponse(Comment comment) {

		CommentResponse response = new CommentResponse();

		response.setId(comment.getId());
		response.setContent(comment.getContent());
		response.setType(comment.getType());
		response.setAuthorId(comment.getAuthor().getId());
		response.setAuthorName(comment.getAuthor().getFullName());
		response.setCreatedAt(comment.getCreatedAt());
		response.setUpdatedAt(comment.getUpdatedAt());

		if (comment.getTicket() != null) {
			response.setTicketId(comment.getTicket().getId());
		}

		if (comment.getTask() != null) {
			response.setTaskId(comment.getTask().getId());
		}

		return response;
	}

	private User getCurrentUser() {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		return userRepository.findByEmail(email)
				.orElseThrow(() -> new IllegalArgumentException("Logged-in user not found"));
	}
}