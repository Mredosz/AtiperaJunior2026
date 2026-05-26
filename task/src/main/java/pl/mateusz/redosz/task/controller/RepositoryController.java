package pl.mateusz.redosz.task.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.mateusz.redosz.task.exception.exceptions.UserRepositoriesNotFound;
import pl.mateusz.redosz.task.model.FullRepositoryDto;
import pl.mateusz.redosz.task.service.RepositoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RepositoryController {
    private final RepositoryService repositoryService;

    @GetMapping("/users/{username}/repositories")
    public ResponseEntity<List<FullRepositoryDto>> getRepositories(@PathVariable String username) throws UserRepositoriesNotFound {
        var repos = repositoryService.getUserRepositories(username);
        return ResponseEntity.ok(repos);
    }
}
