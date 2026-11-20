package com.sumpositive.ticketing.team.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sumpositive.ticketing.team.dto.CreateTeamRequest;
import com.sumpositive.ticketing.team.dto.TeamResponse;
import com.sumpositive.ticketing.team.entity.Team;
import com.sumpositive.ticketing.team.repository.TeamRepository;
import com.sumpositive.ticketing.user.enums.AccountStatus;
import com.sumpositive.ticketing.user.entity.User;
import com.sumpositive.ticketing.user.enums.UserRole;
import com.sumpositive.ticketing.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    @Override
    public TeamResponse createTeam(CreateTeamRequest request) {

        if (teamRepository.existsByTeamName(request.getTeamName())) {
            throw new IllegalArgumentException("Team name already exists");
        }

        User teamLeader = getValidUser(request.getTeamLeaderId());

        if (teamLeader.getUserRole() != UserRole.TEAM_LEADER) {
            throw new IllegalArgumentException(
                "Selected user must have TEAM_LEADER role");
        }

        Team team = new Team();
        team.setTeamName(request.getTeamName());
        team.setDescription(request.getDescription());
        team.setTeamLeader(teamLeader);

        Set<User> members = new HashSet<>();

        if (request.getMemberIds() != null) {
            for (Long memberId : request.getMemberIds()) {
                members.add(getValidUser(memberId));
            }
        }

        members.remove(teamLeader);

        team.setMembers(members);
        team.setCreatedAt(LocalDateTime.now());

        return mapToResponse(teamRepository.save(team));
    }

    @Override
    @Transactional(readOnly = true)
    public TeamResponse getTeamById(Long teamId) {
        Team team = getTeam(teamId);
        return mapToResponse(team);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeamResponse> getAllTeams() {
        return teamRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeamResponse> getTeamsByLeader(Long leaderId) {

        User leader = userRepository.findById(leaderId)
                .orElseThrow(() ->
                    new IllegalArgumentException("Team leader not found"));

        return teamRepository.findByTeamLeader(leader)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TeamResponse addMember(Long teamId, Long userId) {

        Team team = getTeam(teamId);
        User user = getValidUser(userId);

        if (team.getMembers().contains(user)) {
            throw new IllegalArgumentException(
                "User is already a member of this team");
        }

        if (user.equals(team.getTeamLeader())) {
            throw new IllegalArgumentException(
                "Team leader is already assigned to this team");
        }

        team.getMembers().add(user);
        team.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(teamRepository.save(team));
    }

    @Override
    public TeamResponse removeMember(Long teamId, Long userId) {

        Team team = getTeam(teamId);

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                    new IllegalArgumentException("User not found"));

        if (!team.getMembers().remove(user)) {
            throw new IllegalArgumentException(
                "User is not a member of this team");
        }

        team.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(teamRepository.save(team));
    }

    @Override
    public void deleteTeam(Long teamId) {
        Team team = getTeam(teamId);
        teamRepository.delete(team);
    }

    private Team getTeam(Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() ->
                    new IllegalArgumentException("Team not found"));
    }

    private User getValidUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                    new IllegalArgumentException("User not found"));

        if (!user.isActive()) {
            throw new IllegalArgumentException("User account is inactive");
        }

        if (user.getAccountStatus() != AccountStatus.APPROVED) {
            throw new IllegalArgumentException(
                "User account is not approved");
        }

        if (user.getUserRole() == null || user.getUserRole() == UserRole.HR
                || user.getUserRole() == UserRole.ADMIN) {
            throw new IllegalArgumentException(
                "User is not eligible for team membership");
        }

        return user;
    }

    private TeamResponse mapToResponse(Team team) {

        TeamResponse response = new TeamResponse();

        response.setId(team.getId());
        response.setTeamName(team.getTeamName());
        response.setDescription(team.getDescription());

        if (team.getTeamLeader() != null) {
            response.setTeamLeaderId(team.getTeamLeader().getId());
            response.setTeamLeaderName(
                team.getTeamLeader().getFullName());
        }

        response.setMemberIds(
            team.getMembers().stream()
                .map(User::getId)
                .collect(Collectors.toSet()));

        response.setMemberNames(
            team.getMembers().stream()
                .map(User::getFullName)
                .collect(Collectors.toSet()));

        response.setCreatedAt(team.getCreatedAt());
        response.setUpdatedAt(team.getUpdatedAt());

        return response;
    }
}
