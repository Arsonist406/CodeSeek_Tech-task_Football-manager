package dev.footballmanager.mapper;

import dev.footballmanager.dto.TeamDTO;
import dev.footballmanager.model.Team;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.HashSet;

@Mapper(componentModel = "spring")
public interface TeamMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "players", ignore = true)
    Team toEntity(TeamDTO teamDTO);

    @AfterMapping
    default void setPlayersSet(
            @MappingTarget Team team
    ) {
        team.setPlayers(new HashSet<>());
    }

    TeamDTO toDTO(Team team);
}