package com.sumpositive.ticketing.team.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sumpositive.ticketing.team.entity.Team;
import com.sumpositive.ticketing.user.entity.User;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findByTeamName(String teamName);

    boolean existsByTeamName(String teamName);

    List<Team> findByTeamLeader(User teamLeader);

    List<Team> findByMembersContaining(User member);
}
