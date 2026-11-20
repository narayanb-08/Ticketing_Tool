package com.sumpositive.ticketing.comment.service;

import java.util.List;
import com.sumpositive.ticketing.comment.dto.CommentResponse;
import com.sumpositive.ticketing.comment.dto.CreateCommentRequest;

public interface CommentService {

	CommentResponse addComment(CreateCommentRequest request);
	
    List<CommentResponse> getTicketComments(Long ticketId);

    List<CommentResponse> getTaskComments(Long taskId);

    CommentResponse updateComment(Long commentId, String content);

    void deleteComment(Long commentId);
}