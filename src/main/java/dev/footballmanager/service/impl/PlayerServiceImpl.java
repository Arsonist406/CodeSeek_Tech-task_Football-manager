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

import java.time.LocalDate;

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
        Player player = getPlayerOrThrowNotFound(id);
        return playerMapper.toDTO(player);
    }

    private Player getPlayerOrThrowNotFound(
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
        Player player = getPlayerOrThrowNotFound(id);

        updateNameIfNotEquals(player, dto);
        updateSurnameIfNotEquals(player, dto);
        updateBirthDateIfNotEquals(player, dto);
        updateExperienceInMouthsIfNotEquals(player, dto);

        Player savedPlayer = playerRepository.save(player);
        return playerMapper.toDTO(savedPlayer);
    }

    private void updateNameIfNotEquals(
            Player player,
            PlayerDTO dto
    ) {
        String oldName = player.getName();
        String newName = dto.name();
        if (!oldName.equals(newName)) {
            player.setName(newName);
        }
    }

    private void updateSurnameIfNotEquals(
            Player player,
            PlayerDTO dto
    ) {
        String oldSurname = player.getSurname();
        String newSurname = dto.surname();
        if (!oldSurname.equals(newSurname)) {
            player.setSurname(newSurname);
        }
    }

    private void updateBirthDateIfNotEquals(
            Player player,
            PlayerDTO dto
    ) {
        LocalDate oldBirthDate = player.getBirthDate();
        LocalDate newBirthDate = dto.birthDate();
        if (!oldBirthDate.equals(newBirthDate)) {
            player.setBirthDate(newBirthDate);
        }
    }

    private void updateExperienceInMouthsIfNotEquals(
            Player player,
            PlayerDTO dto
    ) {
        int oldExperienceInMouths = player.getExperienceInMouths();
        int newExperienceInMouths = dto.experienceInMouths();
        if (oldExperienceInMouths != newExperienceInMouths) {
            player.setExperienceInMouths(newExperienceInMouths);
        }
    }

    @Override
    @Transactional
    public PlayerDTO addPlayerToTeam(
            Long playerId,
            Long teamId
    ) {
        Player player = getPlayerOrThrowNotFound(playerId);
        Team team = teamService.getTeamOrThrowNotFound(teamId);

        setPlayerTeamIfPlayerNotAlreadyInTeam(player, team);

        Player savedPlayer = playerRepository.save(player);
        return playerMapper.toDTO(savedPlayer);
    }

    private void setPlayerTeamIfPlayerNotAlreadyInTeam(
            Player player,
            Team team
    ) {
        if (player.getTeam() != null) {
            throw new PlayerAlreadyInTeamException("Player with id " + player.getId() + " is already in team.");
        }

        player.setTeam(team);
    }

    @Override
    @Transactional
    public PlayerDTO removePlayerFromTeam(
            Long playerId
    ) {
        Player player = getPlayerOrThrowNotFound(playerId);

        player.setTeam(null);

        Player savedPlayer = playerRepository.save(player);
        return playerMapper.toDTO(savedPlayer);
    }

    @Override
    @Transactional
    public void delete(
            Long id
    ) {
        Player player = getPlayerOrThrowNotFound(id);
        playerRepository.delete(player);
    }
}
