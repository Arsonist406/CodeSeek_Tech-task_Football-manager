package dev.footballmanager.controller;

import dev.footballmanager.dto.TeamDTO;
import dev.footballmanager.dto.TransferInfoDTO;
import dev.footballmanager.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TeamDTO findById(
            @PathVariable("id")
            Long id
    ) {
        return teamService.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<TeamDTO> findAll(
            @PageableDefault(size = 20)
            Pageable pageable
    ) {
        return teamService.findAll(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeamDTO create(
            @RequestBody
            @Valid
            TeamDTO dto
    ) {
        return teamService.create(dto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TeamDTO update(
            @PathVariable("id")
            Long id,
            @RequestBody
            @Valid
            TeamDTO dto
    ) {
        return teamService.update(id, dto);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void transferPlayer(
            @RequestBody
            TransferInfoDTO dto
    ) {
        teamService.transferAPlayer(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable("id")
            Long id
    ) {
        teamService.delete(id);
    }
}
