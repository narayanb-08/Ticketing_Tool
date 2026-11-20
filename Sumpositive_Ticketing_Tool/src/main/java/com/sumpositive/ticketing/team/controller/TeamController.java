package com.sumpositive.ticketing.team.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.sumpositive.ticketing.team.dto.CreateTeamRequest;
import com.sumpositive.ticketing.team.dto.TeamResponse;
import com.sumpositive.ticketing.team.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping("/createTeam")
    @PreAuthorize("@accountStatusSecurity.isApproved() and hasAnyRole('ADMIN', 'PROJECT_MANAGER')")
    public ResponseEntity<TeamResponse> createTeam(
            @Valid @RequestBody CreateTeamRequest request) {

        return new ResponseEntity<>(
                teamService.createTeam(request),
                HttpStatus.CREATED);
    }

    @GetMapping("/getBy/{teamId}")
    @PreAuthorize("@accountStatusSecurity.isApproved()")
    public ResponseEntity<TeamResponse> getTeamById(
            @PathVariable Long teamId) {

        return ResponseEntity.ok(teamService.getTeamById(teamId));
    }

    @GetMapping("/getAll")
    @PreAuthorize("@accountStatusSecurity.isApproved()")
    public ResponseEntity<List<TeamResponse>> getAllTeams() {

        return ResponseEntity.ok(teamService.getAllTeams());
    }

    @GetMapping("/getByLeader/leader/{leaderId}")
    @PreAuthorize("@accountStatusSecurity.isApproved()")
    public ResponseEntity<List<TeamResponse>> getTeamsByLeader(
            @PathVariable Long leaderId) {

        return ResponseEntity.ok(
                teamService.getTeamsByLeader(leaderId));
    }

    @PostMapping("/addMember/{teamId}/members/{userId}")
    @PreAuthorize("@accountStatusSecurity.isApproved() and hasAnyRole('ADMIN', 'PROJECT_MANAGER', 'TEAM_LEADER')")
    public ResponseEntity<TeamResponse> addMember(
            @PathVariable Long teamId,
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                teamService.addMember(teamId, userId));
    }

    @DeleteMapping("/removeMember/{teamId}/members/{userId}")
    @PreAuthorize("@accountStatusSecurity.isApproved() and hasAnyRole('ADMIN', 'PROJECT_MANAGER', 'TEAM_LEADER')")
    public ResponseEntity<TeamResponse> removeMember(
            @PathVariable Long teamId,
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                teamService.removeMember(teamId, userId));
    }

    @DeleteMapping("/deleteBy/{teamId}")
    @PreAuthorize("@accountStatusSecurity.isApproved() and hasAnyRole('ADMIN', 'PROJECT_MANAGER')")
    public ResponseEntity<String> deleteTeam(
            @PathVariable Long teamId) {

        teamService.deleteTeam(teamId);

        return ResponseEntity.ok("Team deleted successfully");
    }
}
