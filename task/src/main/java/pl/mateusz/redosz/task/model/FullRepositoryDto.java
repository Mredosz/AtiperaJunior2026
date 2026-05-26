package pl.mateusz.redosz.task.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record FullRepositoryDto( @JsonProperty("RepositoryName") String name,
                                 @JsonProperty("OwnerLogin")String owner,
                                 List<FullBranchDto> branches) {
}
