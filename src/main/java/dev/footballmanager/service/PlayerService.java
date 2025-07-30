package dev.footballmanager.service;

import dev.footballmanager.dto.PlayerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlayerService {

    PlayerDTO findById(Long id);

    Page<PlayerDTO> findAll(Pageable pageable);

    PlayerDTO create(PlayerDTO dto);

    PlayerDTO update(Long id, PlayerDTO dto);

    PlayerDTO addPlayerToTeam(Long playerId, Long teamId);

    PlayerDTO removePlayerFromTeam(Long playerId);

    void delete(Long id);
}
