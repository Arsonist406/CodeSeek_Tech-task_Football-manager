package dev.footballmanager.dto;

import java.io.Serializable;

public record TransferInfoDTO(
        long sellingTeamId,
        long buyingTeamId,
        long playerId
) implements Serializable {}
