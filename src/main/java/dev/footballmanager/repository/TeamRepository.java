package dev.footballmanager.repository;

import dev.footballmanager.model.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends
        JpaRepository<Team, Long>
{
    @EntityGraph(attributePaths = "players")
    Optional<Team> findById(Long id);

    @EntityGraph(attributePaths = "players")
    Page<Team> findAll(Pageable pageable);
}
