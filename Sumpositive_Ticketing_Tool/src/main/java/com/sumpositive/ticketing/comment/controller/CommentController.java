package com.sumpositive.ticketing.comment.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.sumpositive.ticketing.comment.dto.CommentResponse;
import com.sumpositive.ticketing.comment.dto.CreateCommentRequest;
import com.sumpositive.ticketing.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;

	@PostMapping("/addComment")
	@PreAuthorize("@accountStatusSecurity.isApproved()")
	public ResponseEntity<CommentResponse> addComment(@Valid @RequestBody CreateCommentRequest request) {
		return new ResponseEntity<>(commentService.addComment(request), HttpStatus.CREATED);
	}

	@GetMapping("/getTicketComments/{ticketId}")
	@PreAuthorize("@accountStatusSecurity.isApproved()")
	public ResponseEntity<List<CommentResponse>> getTicketComments(@PathVariable Long ticketId) {
		return ResponseEntity.ok(commentService.getTicketComments(ticketId));
	}

	@GetMapping("/getTaskComments/{taskId}")
	@PreAuthorize("@accountStatusSecurity.isApproved()")
	public ResponseEntity<List<CommentResponse>> getTaskComments(@PathVariable Long taskId) {
		return ResponseEntity.ok(commentService.getTaskComments(taskId));
	}

	@PutMapping("/updateComment/{commentId}")
	@PreAuthorize("@accountStatusSecurity.isApproved()")
	public ResponseEntity<CommentResponse> updateComment(@PathVariable Long commentId, @RequestParam String content) {
		return ResponseEntity.ok(commentService.updateComment(commentId, content));
	}

	@DeleteMapping("/deleteComment/{commentId}")
	@PreAuthorize("@accountStatusSecurity.isApproved()")
	public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {

		commentService.deleteComment(commentId);

		return ResponseEntity.noContent().build();
	}
}