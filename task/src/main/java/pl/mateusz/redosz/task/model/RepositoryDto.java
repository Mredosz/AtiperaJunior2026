package pl.mateusz.redosz.task.model;

public record RepositoryDto(String name,
                            OwnerDto owner,
                            boolean fork) {
}
