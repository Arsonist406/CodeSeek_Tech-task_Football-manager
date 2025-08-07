package dev.footballmanager.service.impl;

import dev.footballmanager.dto.PlayerDTO;
import dev.footballmanager.exception.NotFoundException;
import dev.footballmanager.exception.PlayerAlreadyInTeamException;
import dev.footballmanager.mapper.PlayerMapper;
import dev.footballmanager.model.Player;
import dev.footballmanager.model.Team;
import dev.footballmanager.repository.PlayerRepository;
import dev.footballmanager.service.PlayerService;
import dev.footballmanager.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;
    private final TeamService teamService;

    @Override
    public PlayerDTO findById(
            Long id
    ) {
        Player player = getPlayer(id);
        return playerMapper.toDTO(player);
    }

    private Player getPlayer(
            Long id
    ) {
        return playerRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Player not found by id " + id));
    }

    @Override
    public Page<PlayerDTO> findAll(
            Pageable pageable
    ) {
        Page<Player> playerPage = playerRepository.findAll(pageable);
        return playerPage.map(playerMapper::toDTO);
    }

    @Override
    @Transactional
    public PlayerDTO create(
            PlayerDTO dto
    ) {
        Player newPlayer = playerMapper.toEntity(dto);
        Player savedPlayer = playerRepository.save(newPlayer);
        return playerMapper.toDTO(savedPlayer);
    }

    @Override
    @Transactional
    public PlayerDTO update(
            Long id,
            PlayerDTO dto
    ) {
        Player player = getPlayer(id);

        updateIfChanged(player::getName, player::setName, dto.name());
        updateIfChanged(player::getSurname, player::setSurname, dto.surname());
        updateIfChanged(player::getBirthDate, player::setBirthDate, dto.birthDate());
        updateIfChanged(player::getExperienceInMonths, player::setExperienceInMonths, dto.experienceInMonths());

        Player savedPlayer = playerRepository.save(player);
        return playerMapper.toDTO(savedPlayer);
    }

    private <T> void updateIfChanged(
            Supplier<T> getter,
            Consumer<T> setter,
            T newValue
    ) {
        if (!Objects.equals(getter.get(), newValue)) {
            setter.accept(newValue);
        }
    }

    @Override
    @Transactional
    public PlayerDTO addPlayerToTeam(
            Long playerId,
            Long teamId
    ) {
        Player player = getPlayer(playerId);
        Team team = teamService.getTeam(teamId);

        if (isPlayerAlreadyInTeam(player.getTeam())) {
            throw new PlayerAlreadyInTeamException("Player with id " + player.getId() + " is already in team.");
        }

        player.setTeam(team);
        Player savedPlayer = playerRepository.save(player);
        return playerMapper.toDTO(savedPlayer);
    }

    private boolean isPlayerAlreadyInTeam(
            Team playersTeam
    ) {
        return playersTeam != null;
    }

    @Override
    @Transactional
    public PlayerDTO removePlayerFromTeam(
            Long playerId
    ) {
        Player player = getPlayer(playerId);

        player.setTeam(null);

        Player savedPlayer = playerRepository.save(player);
        return playerMapper.toDTO(savedPlayer);
    }

    @Override
    @Transactional
    public void delete(
            Long id
    ) {
        Player player = getPlayer(id);
        playerRepository.delete(player);
    }
}
