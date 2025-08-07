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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    @Value("${transfer.price.coefficient}")
    private double transferPriceCoefficient;

    @Override
    public TeamDTO findById(
            Long id
    ) {
        Team team = getTeam(id);
        return teamMapper.toDTO(team);
    }

    @Override
    public Team getTeam(
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
        Team team = getTeam(id);

        updateIfChanged(team::getName, team::setName, dto.name());
        updateIfChanged(team::getTransferFeeInPercent, team::setTransferFeeInPercent, dto.transferFeeInPercent());
        updateIfChanged(team::getAccount, team::setAccount, dto.account());

        Team savedTeam = teamRepository.save(team);
        return teamMapper.toDTO(savedTeam);
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
    public void transferAPlayer(
            TransferInfoDTO dto
    ) {
        Team sellingTeam = getTeam(dto.sellingTeamId());
        Player player = getTeamPlayerByIdOrThrowNotFound(sellingTeam, dto.playerId());
        Team buyingTeam = getTeam(dto.buyingTeamId());

        double transferCost = calcTransferCost(player);
        double finalTransferCost = transferCost + (transferCost * sellingTeam.getTransferFeeInPercent() / 100.0);

        if (finalTransferCost > buyingTeam.getAccount()) {
            throw new NotEnoughMoneyOnAccountException("Team that buys player have not enough money on their account");
        }

        buyingTeam.setAccount(buyingTeam.getAccount() - finalTransferCost);
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
        double experienceInMonths = player.getExperienceInMonths();

        return experienceInMonths * transferPriceCoefficient / ageInYears;
    }

    @Override
    @Transactional
    public void delete(
            Long id
    ) {
        Team team = getTeam(id);

        team.getPlayers().forEach(player -> player.setTeam(null));

        teamRepository.delete(team);
    }
}
