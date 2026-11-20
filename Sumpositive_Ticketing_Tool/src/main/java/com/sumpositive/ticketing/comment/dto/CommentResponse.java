package com.sumpositive.ticketing.comment.dto;

import java.time.LocalDateTime;
import com.sumpositive.ticketing.comment.entity.CommentType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponse {

    private Long id;

    private String content;

    private CommentType type;

    private Long authorId;

    private String authorName;

    private Long ticketId;

    private Long taskId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}