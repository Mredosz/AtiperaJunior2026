package pl.mateusz.redosz.task.service;


import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import pl.mateusz.redosz.task.exception.exceptions.UserRepositoriesNotFound;
import pl.mateusz.redosz.task.mapper.Mapper;
import pl.mateusz.redosz.task.model.BranchDto;
import pl.mateusz.redosz.task.model.FullRepositoryDto;
import pl.mateusz.redosz.task.model.RepositoryDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RepositoryService {
    private final RestClient restClient;


    public List<FullRepositoryDto> getUserRepositories(String username) throws UserRepositoriesNotFound {
        return getRepos(username)
                .stream()
                .filter(r -> !r.fork())
                .map(r -> Mapper.map(r, getBranches(username, r.name())))
                .toList();
    }

    private List<RepositoryDto> getRepos(String username) throws UserRepositoriesNotFound {
        try {
            return restClient
                    .get()
                    .uri("/users/{username}/repos", username)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserRepositoriesNotFound();
        }
    }

    private List<BranchDto> getBranches(String username, String repoName){
        return restClient
                .get()
                .uri("/repos/{username}/{repoName}/branches", username, repoName)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }
}
