package dev.footballmanager.repository;

import dev.footballmanager.model.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends
        JpaRepository<Player, Long>
{
    @EntityGraph(attributePaths = "team")
    Optional<Player> findById(Long id);

    @EntityGraph(attributePaths = "team")
    Page<Player> findAll(Pageable pageable);
}
