package com.sumpositive.ticketing.comment.dto;

import com.sumpositive.ticketing.comment.entity.CommentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCommentRequest {

    @NotBlank(message = "Comment content is required")
    @Size(max = 2000, message = "Comment cannot exceed 2000 characters")
    private String content;

    private CommentType type = CommentType.PUBLIC;

    private Long ticketId;

    private Long taskId;
}