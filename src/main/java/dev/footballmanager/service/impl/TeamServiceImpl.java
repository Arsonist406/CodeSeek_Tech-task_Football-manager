package dev.footballmanager.service.impl;

import dev.footballmanager.dto.TeamDTO;
import dev.footballmanager.dto.TransferInfoDTO;
import dev.footballmanager.exception.NotEnoughMoneyOnAccountException;
import dev.footballmanager.exception.NotFoundException;
import dev.footballmanager.mapper.TeamMapper;
import dev.footballmanager.model.Player;
import dev.footballmanager.model.Team;
import dev.footballmanager.repository.TeamRepository;
import dev.footballmanager.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    @Override
    public TeamDTO findById(
            Long id
    ) {
        Team team = getTeamOrThrowNotFound(id);
        return teamMapper.toDTO(team);
    }

    @Override
    public Team getTeamOrThrowNotFound(
            Long id
    ) {
        return teamRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Team not found by id " + id));
    }

    @Override
    public Page<TeamDTO> findAll(
            Pageable pageable
    ) {
        Page<Team> teamPage = teamRepository.findAll(pageable);
        return teamPage.map(teamMapper::toDTO);
    }

    @Override
    @Transactional
    public TeamDTO create(
            TeamDTO dto
    ) {
        Team newTeam = teamMapper.toEntity(dto);
        Team savedTeam = teamRepository.save(newTeam);
        return teamMapper.toDTO(savedTeam);
    }

    @Override
    @Transactional
    public TeamDTO update(
            Long id,
            TeamDTO dto
    ) {
        Team team = getTeamOrThrowNotFound(id);

        updateNameIfNotEquals(team, dto);
        updateTransferFeeIfNotEquals(team, dto);
        updateAccountIfNotEquals(team, dto);

        Team savedTeam = teamRepository.save(team);
        return teamMapper.toDTO(savedTeam);
    }

    private void updateNameIfNotEquals(
            Team team,
            TeamDTO dto
    ) {
        String oldName = team.getName();
        String newName = dto.name();
        if (!oldName.equals(newName)) {
            team.setName(newName);
        }
    }

    private void updateTransferFeeIfNotEquals(
            Team team,
            TeamDTO dto
    ) {
        double oldTransferFee = team.getTransferFee();
        double newTransferFee = dto.transferFee();
        if (oldTransferFee != newTransferFee) {
            team.setTransferFee(newTransferFee);
        }
    }

    private void updateAccountIfNotEquals(
            Team team,
            TeamDTO dto
    ) {
        double oldAccount = team.getAccount();
        double newAccount = dto.account();
        if (oldAccount != newAccount) {
            team.setAccount(newAccount);
        }
    }

    @Override
    @Transactional
    public void transferAPlayer(
            TransferInfoDTO dto
    ) {
        Team sellingTeam = getTeamOrThrowNotFound(dto.sellingTeamId());
        Player player = getTeamPlayerByIdOrThrowNotFound(sellingTeam, dto.playerId());
        Team buyingTeam = getTeamOrThrowNotFound(dto.buyingTeamId());

        double transferCost = calcTransferCost(player);
        double finalTransferCost = transferCost + (transferCost * sellingTeam.getTransferFee() / 100.0);

        withdrawFinalTransferCostFromBuyingTeamAccount(buyingTeam, finalTransferCost);
        sellingTeam.setAccount(sellingTeam.getAccount() + finalTransferCost);
        player.setTeam(buyingTeam);
    }

    private Player getTeamPlayerByIdOrThrowNotFound(
            Team team,
            long playerId
    ) {
        return team.getPlayers()
                .stream()
                .filter(p -> p.getId() == playerId)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Player not a member of the team"));
    }

    private double calcTransferCost(
            Player player
    ) {
        Period period = Period.between(player.getBirthDate(), LocalDate.now());
        int ageInYears = period.getYears();
        double experienceInMouths = player.getExperienceInMouths();

        return experienceInMouths * 100000 / ageInYears;
    }

    private void withdrawFinalTransferCostFromBuyingTeamAccount(
            Team buyingTeam,
            double finalTransferCost
    ) {
        if (finalTransferCost > buyingTeam.getAccount()) {
            throw new NotEnoughMoneyOnAccountException("Team that buys player have not enough money on their account");
        }

        buyingTeam.setAccount(buyingTeam.getAccount() - finalTransferCost);
    }

    @Override
    @Transactional
    public void delete(
            Long id
    ) {
        Team team = getTeamOrThrowNotFound(id);

        team.getPlayers().forEach(player -> player.setTeam(null));

        teamRepository.delete(team);
    }
}
