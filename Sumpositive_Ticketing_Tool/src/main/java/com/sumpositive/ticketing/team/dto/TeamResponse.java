package com.sumpositive.ticketing.team.dto;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;

@Data
public class TeamResponse {

    private Long id;

    private String teamName;

    private String description;

    private Long teamLeaderId;

    private String teamLeaderName;

    private Set<Long> memberIds;

    private Set<String> memberNames;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
