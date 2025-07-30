package dev.footballmanager.service;

import dev.footballmanager.dto.TeamDTO;
import dev.footballmanager.dto.TransferInfoDTO;
import dev.footballmanager.model.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeamService {

    TeamDTO findById(Long id);

    Team getTeam(Long id);

    Page<TeamDTO> findAll(Pageable pageable);

    TeamDTO create(TeamDTO dto);

    TeamDTO update(Long id, TeamDTO dto);

    void transferAPlayer(TransferInfoDTO dto);

    void delete(Long id);
}
