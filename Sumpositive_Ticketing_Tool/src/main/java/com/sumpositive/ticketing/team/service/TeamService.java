package com.sumpositive.ticketing.team.service;

import java.util.List;
import com.sumpositive.ticketing.team.dto.CreateTeamRequest;
import com.sumpositive.ticketing.team.dto.TeamResponse;

public interface TeamService {

    TeamResponse createTeam(CreateTeamRequest request);

    TeamResponse getTeamById(Long teamId);

    List<TeamResponse> getAllTeams();

    List<TeamResponse> getTeamsByLeader(Long leaderId);

    TeamResponse addMember(Long teamId, Long userId);

    TeamResponse removeMember(Long teamId, Long userId);

    void deleteTeam(Long teamId);
}
