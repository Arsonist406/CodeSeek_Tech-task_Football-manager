package dev.footballmanager.controller;

import dev.footballmanager.dto.PlayerDTO;
import dev.footballmanager.service.PlayerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PlayerDTO findById(
            @PathVariable("id")
            Long id
    ) {
        return playerService.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<PlayerDTO> findAll(
            @PageableDefault(size = 20)
            Pageable pageable
    ) {
        return playerService.findAll(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlayerDTO create(
            @RequestBody
            @Valid
            PlayerDTO dto
    ) {
        return playerService.create(dto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PlayerDTO update(
            @PathVariable("id")
            Long id,
            @RequestBody
            @Valid
            PlayerDTO dto
    ) {
        return playerService.update(id, dto);
    }

    @PatchMapping("/{player_id}/team/{team_id}")
    @ResponseStatus(HttpStatus.OK)
    public PlayerDTO addPlayerToTeam(
            @PathVariable("player_id")
            Long playerId,
            @PathVariable("team_id")
            Long teamId
    ) {
        return playerService.addPlayerToTeam(playerId, teamId);
    }

    @PatchMapping("/{player_id}")
    @ResponseStatus(HttpStatus.OK)
    public PlayerDTO removePlayerFromTeam(
            @PathVariable("player_id")
            Long playerId
    ) {
        return playerService.removePlayerFromTeam(playerId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable("id")
            Long id
    ) {
        playerService.delete(id);
    }
}
