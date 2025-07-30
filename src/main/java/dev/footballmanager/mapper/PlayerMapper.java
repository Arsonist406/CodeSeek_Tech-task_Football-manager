package dev.footballmanager.mapper;

import dev.footballmanager.dto.PlayerDTO;
import dev.footballmanager.model.Player;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "team", ignore = true)
    Player toEntity(PlayerDTO playerDTO);

    PlayerDTO toDTO(Player player);
}