package pl.mateusz.redosz.task.model;

import lombok.Builder;

@Builder
public record FullBranchDto(String name, String sha) {
}
